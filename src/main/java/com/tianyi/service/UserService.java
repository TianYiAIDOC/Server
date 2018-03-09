package com.tianyi.service;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.tianyi.bo.SMSCode;
import com.tianyi.bo.User;
import com.tianyi.bo.UserSession;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.SexEnum;
import com.tianyi.bo.enums.UserStatusEnum;
import com.tianyi.bo.enums.UserType;
import com.tianyi.dao.UserDao;
import com.tianyi.dao.UserSessionDao;
import com.tianyi.bo.UserDataResult;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.EncryptionUtil;
import com.tianyi.web.util.Send253SMS;
import com.tianyi.web.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Convert;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("userService")
public class UserService {
    @Resource
    UserSessionDao userSessionDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SMSCodeService smsCodeService;
    @Resource
    private Send253SMS send253SMS;
    @Resource
    I18nService i18nService;



    public User getUserByUserName(String userName, UserType userType) {
        return userDao.getUserByUsername(userName, userType);
    }

    public User getUserByUserName(String username) {
        return userDao.getUserByUsername(username);
    }

    public User getUserByMobile(int countryCode,String mobile, UserType userType) {
        return userDao.getUserByMobile(countryCode,mobile, userType);
    }


    public User getUserByMobile(int countryCode,String mobile) {
        return userDao.getUserByMobile(countryCode,mobile);
    }

    public User getUserByUserId(long userId) {
        User user = userDao.getById(userId);
        return user;
    }


    public User register( String mobile, String password,String lang,int counyryCode) {

        if (StringUtils.isBlank(password) || password.length() < 6) {
            throw new RuntimeException(i18nService.getMessage("" + 117,lang));
        }

        if (getUserByMobile(counyryCode,mobile) != null) {
            throw new RuntimeException(i18nService.getMessage("" + 110,lang));
        }

        User user = new User();
        user.setPasswordHash(EncryptionUtil.SHA256(password));
        user.setMobile(mobile);
        user.setNickname(mobile);
        user.setUserStatus(UserStatusEnum.EFFECTIVE);
        user.setUserType(UserType.USER);
        user.setCountryCode(counyryCode);
        Long id = (Long) userDao.add(user);

     //   easeMobService.createUser(String.valueOf(id),password);

        return userDao.getById(id);
    }

    public void sendVerificationCodeViaSMS(int counyryCode ,String mobilePhoneNumber, int sendType,String lang){
        String code = Tools.getRandomNumber(6);

        SendSmsResponse result =null;
        if(sendType == 0) {
            User user = getUserByMobile(counyryCode,mobilePhoneNumber);
            if (user != null) {
                throw new RuntimeException(i18nService.getMessage("" + 110,lang));
            }
            result = send253SMS.register(counyryCode,mobilePhoneNumber, code, LanguageEnum.valueOf(lang));
        }else if(sendType == 1){
            result = send253SMS.chengePassword(counyryCode,mobilePhoneNumber, code, LanguageEnum.valueOf(lang));
        }else if(sendType == 2){
            result = send253SMS.chengeAccount(counyryCode,mobilePhoneNumber, code, LanguageEnum.valueOf(lang));
        }

        else{
            throw new RuntimeException(i18nService.getMessage("" + 118,lang));
        }
        smsCodeService.addSMSCodeLong(mobilePhoneNumber, code, JSON.toJSONString(result));
        if (result ==null || !"0".equals(result.getCode())) {
            throw new RuntimeException(i18nService.getMessage("" + 122,lang));
        }
        smsCodeService.addSMSCode(counyryCode+mobilePhoneNumber, code);
    }

    public boolean validateVerificationCode(int countryCode ,String mobilePhoneNumber, String code,String lang) {

//        if(true){
//            return true;
//        }

        SMSCode smsCode = smsCodeService.getSMSCodeByCode(code);
        if (smsCode == null || !smsCode.getMobile().equals(countryCode+mobilePhoneNumber)) {
            throw new RuntimeException(i18nService.getMessage("" + 121,lang));
        }
        if((smsCode.getCreatedOn().getTime() - DateUtil.getCurrentDate().getTime()) > 5*60*1000){
            smsCodeService.delSMSCode(smsCode);
            throw new RuntimeException(i18nService.getMessage("" + 120,lang));
        }
        smsCodeService.delSMSCode(smsCode);
        return true;
    }

    public void changePassword(int counyryCode,String mobile, String newPassword, UserType userType) {
        User user = userDao.getUserByMobile(counyryCode,mobile,userType);
        if (user != null) {
            user.setPasswordHash(EncryptionUtil.SHA256(newPassword));
            userDao.update(user);
         //   easeMobService.updatePassword(String.valueOf(user.getId()),newPassword);
        }
    }
    public void changeAccount(int oldCounyryCode,int newCounyryCode,String oldPhone,String mobile, String password, UserType userType) {
        User newUser = userDao.getUserByMobile(newCounyryCode,mobile,userType);
        if (newUser != null) {
            throw new RuntimeException("手机号已经注册！！！");
        }


        User user = userDao.getUserByMobile(oldCounyryCode,oldPhone,userType);
        if (user== null) {
            throw new RuntimeException("用户不存在！！！");
        }
        if(!user.getPasswordHash().equals(EncryptionUtil.SHA256(password))){
            throw new RuntimeException("旧密码错误！！！");
        }
            user.setMobile(mobile);
            userDao.update(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }


    public UserSession createUserSession(long userId) {
        UserSession userSession = userSessionDao.getUserSessionByUserId(userId);
        if (userSession != null) {
            userSession.setToken(UUID.randomUUID().toString());
            userSessionDao.update(userSession);
        } else {
            userSession = new UserSession();
            userSession.setUserId(userId);
            userSession.setToken(UUID.randomUUID().toString());
            userSessionDao.add(userSession);
        }
        return userSession;
    }


    public User getUserByToken(String token) {
        UserSession userSession = userSessionDao.getUserSessionByToken(token);
        if (userSession == null) {
            return null;
        }
        return userDao.getById(userSession.getUserId());
    }


    public UserSession getUserSessionByToken(String token) {
        return userSessionDao.getUserSessionByToken(token);
    }


    public void deleteUserSession(UserSession userSession) {
        userSessionDao.delete(userSession);
    }


    public List<User> getUserByUserType(UserType userType, int page, int pageSize) {
        return userDao.getUserList(userType, page, pageSize);
    }

    public void setUserStatus(long userId, boolean enabled) {
        User user = getUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setUserStatus(enabled == true ? UserStatusEnum.EFFECTIVE : UserStatusEnum.INVALID);
        userDao.update(user);
    }


    public void delUser(long userId) {
        User user = getUserByUserId(userId);
        if (user != null) {
            userDao.delete(user);
        }
    }


    public Integer getUserTotalNum(UserType userType) {
        return userDao.getUserTotalNum(userType).intValue();
    }


    public List<User> getUserList(UserType userType, int page, int pageSize, int start_areaId, int end_areaId, String keyword, UserStatusEnum userStatus,
                                   SexEnum sex ) {
        return userDao.getUserList(userType, page, pageSize, start_areaId, end_areaId, keyword,userStatus,sex );
    }


    public long getTotalUserNumber(UserType userType, int start_areaId, int end_areaId, String keyword,UserStatusEnum userStatus,
                                   SexEnum sex ) {
        return userDao.getTotalUserNumber(userType, start_areaId, end_areaId, keyword,userStatus,sex);
    }


    public long getTotalUserNumber(UserType userType, Date start_Date, Date end_Date) {
        return userDao.getTotalUserNumber(userType, start_Date, end_Date);
    }


    public User addUser(int counyryCode,String phone, String username, String password, UserType userTypeEnum,String reqlName) {

        if (getUserByUserName(username) != null) {
            throw new RuntimeException("用户名被占用");
        }
        if (getUserByMobile(counyryCode,phone) != null) {
            throw new RuntimeException("手机号被占用");
        }

        User user = new User();
        user.setPasswordHash(EncryptionUtil.SHA256(password));
        user.setUsername(username);
        user.setNickname(username);
        user.setMobile(phone);
        user.setUserStatus(UserStatusEnum.EFFECTIVE);
        user.setUserType(userTypeEnum);
        user.setRealName(reqlName);

        userDao.add(user);
        User addUser = userDao.getById(user.getId());

        //修改成hash加密
        user.setPasswordHash(EncryptionUtil.SHA256(password));
        userDao.update(user);


    //    easeMobService.createUser(String.valueOf(addUser.getId()),password);

        return addUser;
    }


    public User updateUser(long userId,int counyryCode,String phone, String username, String password, UserType userTypeEnum) {

//        if (getUserByUserName(username) != null) {
//            throw new RuntimeException("用户名被占用");
//        }
        if (getUserByMobile(counyryCode,phone) != null) {
            throw new RuntimeException("手机号被占用");
        }

        User user = getUserByUserId(userId);
        user.setPasswordHash(EncryptionUtil.SHA256(password));
        user.setUsername(username);
//        user.setNickname(niceName);
        user.setMobile(phone);
        user.setUserType(userTypeEnum);

        userDao.add(user);
        User addUser = userDao.getById(user.getId());

        //修改成hash加密
        user.setPasswordHash(EncryptionUtil.SHA256(password));
        userDao.update(user);


    //    easeMobService.createUser(String.valueOf(addUser.getId()),password);


     //   //修改环信昵称
    //    easeMobService.updateNikeName(String.valueOf(user.getId()), username);
        return addUser;
    }






    public void updateUserInfo(long userId ,String real_name,int city_id,String address,String avatar,String sign,Date birthday,String nikeName){
        User user = getUserByUserId(userId);
        if(user ==null){
            throw new RuntimeException("用户不存在!!!");
        }

        if(StringUtils.isNotBlank(real_name)){
            user.setRealName(real_name);
        }
        if(StringUtils.isNotBlank(nikeName)){
            user.setNickname(nikeName);
        }

        user.setAreaId(city_id);
        if(StringUtils.isNotBlank(address)) {
            user.setAddress(address);
        }
        if(StringUtils.isNotBlank(avatar)) {
            user.setAvatar(avatar);
        }
        if(StringUtils.isNotBlank(sign)) {
            user.setSignature(sign);
        }


        updateUser(user);
    }

    public List<UserDataResult> getUserRanking(){

        List<UserDataResult> results = userDao.getUserRanking();

        List<UserDataResult> returnResult = new ArrayList<>();

        for(Object result:(ArrayList) results){

            Object[] os= (Object[])result;

            UserDataResult ur = new UserDataResult();




            ur.setUserId((BigInteger)os[3]);
            ur.setMobile( (String)os[0]);
            ur.setNickname((String)os[1]);
            ur.setDataNumber((Integer)os[4]);
            ur.setSignature((String)os[2]);
            ur.setAvatar((String)os[5]);

            returnResult.add(ur);

        }

        return returnResult;
    }

    public List<UserDataResult> getUserTotalRanking(){

        List<UserDataResult> results = userDao.getUserTotalRanking();

        List<UserDataResult> returnResult = new ArrayList<>();

        for(Object result:(ArrayList) results){

            Object[] os= (Object[])result;

            UserDataResult ur = new UserDataResult();




            ur.setUserId((BigInteger)os[3]);
            ur.setMobile( (String)os[0]);
            ur.setNickname((String)os[1]);
            BigDecimal number=(BigDecimal)os[4];
            ur.setDataNumber(number.intValue());
            ur.setSignature((String)os[2]);
            ur.setAvatar((String)os[5]);

            returnResult.add(ur);

        }

        return returnResult;
    }
}