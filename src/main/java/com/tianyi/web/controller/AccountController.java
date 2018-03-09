package com.tianyi.web.controller;

import com.tianyi.bo.*;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.bo.enums.UserStatusEnum;
import com.tianyi.bo.enums.UserType;
import com.tianyi.service.*;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.UnauthorizedException;
import com.tianyi.web.controller.vo.ClientEunm;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.AdminUserModel;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.web.model.UserCommonModel;
import com.tianyi.web.model.account.LoginResult;
import com.tianyi.web.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class AccountController {
    @Value("${is.open.cms}")
    public String isOpenCms;
    @Resource
    private UserService userService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private SendSMS sendSMS;
    @Resource
    private AreaService areaService;
    @Autowired
    private UserDayDataService userDayDataService;
    @Resource
    AccountService accountService;
    @Resource
    UserDataService userDataService;
    @Autowired
    I18nService i18nService;
    @Resource
    CountryService countryService;

    @RequestMapping(value = "/account/register", method = RequestMethod.POST)
    public Map<String, Object> register(@JsonPathArg("password") String password,
                                        @JsonPathArg("phone") String phone,
                                        @JsonPathArg("phone_verify") String phoneVerifyCode,
                                        @JsonPathArg("country_code") Integer countryCode,
                                        @Value("#{request.getAttribute('lang')}") String lang,
                                        HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException(i18nService.getMessage("" + 101,lang));
        }
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException(i18nService.getMessage("" + 102,lang));
        }
        if (countryCode ==null) {
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }

        if (!userService.validateVerificationCode(countryCode,phone, phoneVerifyCode,lang) && "true" .equals(isOpenCms)) {
            throw new RuntimeException(i18nService.getMessage("" + 103,lang));
        }
//        HttpSession httpSession = request.getSession();
//        Object code = httpSession.getAttribute("_CAPTCHA");
//        if (code == null || !code.toString().equalsIgnoreCase(picVerifyCode)) {
//            throw new RuntimeException("验证码错误");
//        }


        User user = userService.register(phone, password,lang,countryCode);
        UserSession userSession = userService.createUserSession(user.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("token", userSession.getToken());
        // result.put("temp_mob_password",password);
        return result;
    }

    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
    public LoginResult login(@JsonPathArg("username") String username,
                             @JsonPathArg("password") String password,
                             @RequestHeader("X-Client") String from,
                             @Value("#{request.getAttribute('lang')}") String lang,
                             @JsonPathArg(value = "country_code",optional = true) Integer countryCode,
                             HttpServletRequest request) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new RuntimeException(i18nService.getMessage("" + 104,lang));
        }

        if(ClientEunm.ADMIN.name().equals(from)){
            countryCode = 86;
        }

        if (countryCode ==null ) {
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }

        User user = userService.getUserByMobile(countryCode,username);

        if (user == null) {
            throw new RuntimeException(i18nService.getMessage("" + 105,lang));
        }
        if (!user.getPasswordHash().equals(EncryptionUtil.SHA256(password))) {
            throw new RuntimeException(i18nService.getMessage("" + 106,lang));
        }
        if (user.getUserStatus() == UserStatusEnum.INVALID) {
            throw new RuntimeException(i18nService.getMessage("" + 107,lang));
        }

        user.setLonginIp(getIpAddr(request));
        user.setLonginNum(user.getLonginNum() + 1);
        userService.updateUser(user);


        UserSession userSession = userService.createUserSession(user.getId());
        LoginResult loginResult = getLoginResultFrom(user);
        loginResult.setToken(userSession.getToken());
        loginResult.setMsg_count(noticeService.getTotalNum(user.getId(), false, null));

        UserDayData userDayData = userDayDataService.getUserDayDataByDay(user.getId(), UserDayEnum.SIGN, DateUtil.format(DateUtil.getCurrentDate(), DateUtil.C_DATE_PATTON_DEFAULT));
        loginResult.setIsSign(userDayData == null ? 0 : 1);

//        String tempPassword = String.valueOf(10000000+user.getLonginNum());
//        easeMobService.updatePassword(String.valueOf(user.getId()),tempPassword);
        return loginResult;
    }


    @AuthRequired
    @RequestMapping("/account/current")
    public LoginResult current(@Value("#{request.getAttribute('currentUser')}") User currentUser) {
       // System.out.println("-------------"+countryService.getAreaName(3021,LanguageEnum.zh));
        return getLoginResultFrom(currentUser);
    }

    private LoginResult getLoginResultFrom(User user) {
        LoginResult loginResult = new LoginResult();


        loginResult.setType(user.getUserType().ordinal());
        loginResult.setUsername(user.getMobile());
        loginResult.setId(user.getId());
        loginResult.setAvatar(user.getAvatar());
        loginResult.setNickname(user.getNickname());
        loginResult.setAddress(user.getAddress());
        loginResult.setSignature(user.getSignature());
        loginResult.setPhone_number(user.getMobile());
        loginResult.setRealname(user.getRealName());
        loginResult.setCity_id(user.getAreaId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getCurrentDate());
        calendar.add(Calendar.DATE, -1);

        long yesrterday = accountService.getDayMoney(user.getId(), DateUtil.format(calendar.getTime(), DateUtil.C_DATE_PATTON_DEFAULT));

        loginResult.setYesterdayAidoc(Tools.getDecimalFour(Tools.toDoulbe(yesrterday) / 1000));

        Account account = accountService.getAccountByUserId(user.getId());

        loginResult.setTotalAidoc(account == null ? 0 : Tools.getDecimalFour((Tools.toDoulbe(account.getBalance()) / 1000)));



        loginResult.setCity_name(countryService.getAreaName(user.getAreaId(),LanguageEnum.values()[user.getUserLanguage()]));


        UserData userData = userDataService.getUserDataByUserId(user.getId());
        loginResult.setSex(userData.getSex().ordinal());
        loginResult.setBirthday(userData.getBirthday() == null ? "" : DateUtil.format(userData.getBirthday(), DateUtil.C_DATE_PATTON_DEFAULT));

        loginResult.setLanguage(user.getUserLanguage());
        loginResult.setCountry_code(user.getCountryCode());

        return loginResult;
    }


    //    @AuthRequired
    @RequestMapping("/account/logout")
    public ActionResult logout(@RequestHeader("X-Token") String currentToken,
                               @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        UserSession userSession = userService.getUserSessionByToken(currentToken);
        if (userSession != null) {
            userService.deleteUserSession(userSession);
        }


        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/account/password", method = RequestMethod.PUT)
    public ActionResult editPassword(@JsonPathArg("old_password") String oldPassword,
                                     @JsonPathArg("new_password") String newPassword,
                                     @Value("#{request.getAttribute('lang')}") String lang,
                                     @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        if (!currentUser.getPasswordHash().equals(EncryptionUtil.SHA256(oldPassword))) {
            throw new RuntimeException(i18nService.getMessage("" + 108,lang));
        }

        userService.changePassword(0,currentUser.getMobile(), newPassword, currentUser.getUserType());
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/account/reset_account", method = RequestMethod.PUT)
    public ActionResult editAccount(@JsonPathArg("old_country_code") Integer oldCountryCode,
                                    @JsonPathArg("new_country_code") Integer newCountryCode,
                                    @JsonPathArg("phone") String phone,
                                    @JsonPathArg("password") String pssword,
                                    @JsonPathArg("phone_verify_code") String phone_verify_code,
                                    @Value("#{request.getAttribute('lang')}") String lang,
                                    @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        if (!userService.validateVerificationCode(newCountryCode,phone, phone_verify_code,lang) && "true" .equals(isOpenCms)) {
            throw new RuntimeException(i18nService.getMessage("" + 103,lang));
        }

        userService.changeAccount(oldCountryCode,newCountryCode,currentUser.getMobile(), phone, pssword, currentUser.getUserType());
        return new ActionResult();
    }


    @RequestMapping(value = "/account/find/reset_password", method = RequestMethod.POST)
    public ActionResult resetPassword(@JsonPathArg("country_code") Integer countryCode,
                                      @JsonPathArg("password") String password,
                                      @JsonPathArg("phone_verify_code") String phone_verify_code,
                                      @Value("#{request.getAttribute('lang')}") String lang,
                                      @JsonPathArg("phone") String phone) {
        User user = userService.getUserByMobile(countryCode,phone);
        if (user == null) {
            throw new UnauthorizedException(i18nService.getMessage("" + 109,lang));
        }

        if (!userService.validateVerificationCode(countryCode,phone, phone_verify_code,lang) && "true" .equals(isOpenCms)) {
            throw new RuntimeException(i18nService.getMessage("" + 103,lang));
        }
        userService.changePassword(countryCode,user.getMobile(), password, user.getUserType());
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/account/current/phone", method = RequestMethod.PUT)
    public LoginResult editPhone(@JsonPathArg("password") String password,
                                 @JsonPathArg("phone") String phone,
                                 @JsonPathArg("country_code") Integer countryCode,
                                 @Value("#{request.getAttribute('lang')}") String lang,
                                 @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        //userService.changePassword(mobile, password);
        if (!currentUser.getPasswordHash().equals(EncryptionUtil.SHA256(String.valueOf(currentUser.getId()) + password))) {
            throw new RuntimeException(i18nService.getMessage("" + 108,lang));
        }
        User user = userService.getUserByMobile(countryCode,phone, currentUser.getUserType());
        if (user != null) {
            throw new RuntimeException(i18nService.getMessage("" + 110,lang));
        }
        currentUser.setMobile(phone);
        userService.updateUser(currentUser);
        return new LoginResult();
    }
    //重置环信密码
//    @AuthRequired
//    @RequestMapping(value = "/user/easeMob/password", method = RequestMethod.GET)
//    public Map<String,Object> getUserEaseMobByUserId(@Value("#{request.getAttribute('currentUser')}") User currentUser) {
//
//        String tempPassword = Tools.getCharacterAndNumber(6);
//        easeMobService.updatePassword(String.valueOf(currentUser.getId()),tempPassword);
//        Map<String,Object> result = new HashedMap();
//        result.put("temp_password",tempPassword);
//        return result;
//    }


    @RequestMapping("/verify_code/phone")
    public ActionResult verify_code_captcha(@JsonPathArg("phone") String mobile,
//                                            @JsonPathArg("captcha") String captcha,
                                            @JsonPathArg("type") Integer type,
                                            @JsonPathArg("country_code") Integer countryCode,
                                            @Value("#{request.getAttribute('lang')}") String lang,
                                            HttpServletRequest request) throws Exception {
//        HttpSession httpSession = request.getSession();
//        Object code = httpSession.getAttribute("_CAPTCHA");
//        if (code == null || !code.toString().equalsIgnoreCase(captcha)) {
//            throw new RuntimeException("验证码错误");
//        }
        userService.sendVerificationCodeViaSMS(countryCode,mobile, type,lang);
        return new ActionResult();
    }

    @RequestMapping(value = "/account/find/verify", method = RequestMethod.POST)
    public Map<String, String> find_verify_code(@JsonPathArg("phone") String phone,
                                                @JsonPathArg("country_code") Integer countryCode,
                                                @JsonPathArg("phone_verify_code") String phone_verify_code,
                                                @Value("#{request.getAttribute('lang')}") String lang,
                                                HttpServletRequest request) {
        if (!userService.validateVerificationCode(countryCode,phone, phone_verify_code,lang) && "true" .equals(isOpenCms)) {
            throw new RuntimeException(i18nService.getMessage("" + 103,lang));
        }
        User user = userService.getUserByMobile(countryCode,phone);
        if (user == null) {
            throw new RuntimeException(i18nService.getMessage("" + 111,lang));
        }
        UserSession userSession = userService.createUserSession(user.getId());

        Map<String, String> map = new HashMap<>();
        map.put("find_token", userSession.getToken());

        return map;
    }

    @AuthRequired
    @RequestMapping(value = "/account/avatar", method = RequestMethod.PUT)
    public ActionResult editAppHeader(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                      @Value("#{request.getAttribute('lang')}") String lang,
                                      @JsonPathArg("avatar") String avatarFile) {
        if (StringUtils.isBlank(avatarFile)) {
            throw new RuntimeException(i18nService.getMessage("" + 112,lang));
        }
        currentUser.setAvatar(avatarFile);
        userService.updateUser(currentUser);

        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/account/nick", method = RequestMethod.PUT)
    public ActionResult editNicekName(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                      @Value("#{request.getAttribute('lang')}") String lang,
                                      @JsonPathArg("nickname") String nickname) {
        if (StringUtils.isBlank(nickname)) {
            throw new RuntimeException(i18nService.getMessage("" + 112,lang));
        }
        currentUser.setNickname(nickname);
        userService.updateUser(currentUser);

        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/account/area", method = RequestMethod.PUT)
    public ActionResult editArea(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                 @JsonPathArg("area_id") int areaId) {

        currentUser.setAreaId(areaId);
        userService.updateUser(currentUser);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/account/signature", method = RequestMethod.PUT)
    public ActionResult editArea(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                 @Value("#{request.getAttribute('lang')}") String lang,
                                 @JsonPathArg("signature") String signature) {


        if (StringUtils.isBlank(signature)) {
            throw new RuntimeException(i18nService.getMessage("" + 112,lang));
        }
        currentUser.setSignature(signature);
        userService.updateUser(currentUser);

        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators", method = RequestMethod.GET)
    public PagedListModel<List<AdminUserModel>> getUserAdministratorss(@RequestParam(value = "p", required = false) Integer page,
                                                                       @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<User> users = userService.getUserByUserType(UserType.ADMIN, page, pageSize);
        int total = userService.getUserTotalNum(UserType.ADMIN);
        List<AdminUserModel> result = new ArrayList<>();
        for (User user : users) {
            UserData userData = userDataService.getUserDataByUserId(user.getId());
            result.add(getAdminUserModel(user,userData));
        }
        return new PagedListModel(result, total, page, pageSize);
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators", method = RequestMethod.POST)
    public AdminUserModel addUserAdministrators(@JsonPathArg(value = "country_code",optional = true) Integer countryCode,
                                                @JsonPathArg("user_name") String username,
                                                @JsonPathArg("phone_number") String phone_number,
                                                @JsonPathArg("real_name") String realName,
                                                @JsonPathArg("password") String password,
                                                @Value("#{request.getAttribute('lang')}") String lang,
                                                @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        if(countryCode ==null){
            countryCode = 86;
        }

        User user = userService.addUser(countryCode,phone_number, username, password, UserType.ADMIN, realName);
        if (user == null) {
            throw new RuntimeException(i18nService.getMessage("" + 111,lang));
        }

        UserData userData = userDataService.getUserDataByUserId(user.getId());
        return getAdminUserModel(user,userData);
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators/{id}", method = RequestMethod.GET)
    public AdminUserModel getUserAdministratorsById(@PathVariable("id") long userId) {
        User user = userService.getUserByUserId(userId);
        UserData userData = userDataService.getUserDataByUserId(user.getId());
        return getAdminUserModel(user,userData);
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators/{id}", method = RequestMethod.PUT)
    public AdminUserModel updateUserAdministrators(@PathVariable("id") long userId,
//                                                   @JsonPathArg("nickname") String nickname,
                                                   @JsonPathArg("country_code") Integer countryCode,
                                                   @JsonPathArg("username") String username,
                                                   @JsonPathArg("phone_number") String phone_number,
                                                   @JsonPathArg("password") String password,
                                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        if(countryCode == null){
            countryCode = 86;
        }
        User user = userService.updateUser(userId,countryCode, phone_number, username, password, UserType.ADMIN);
        UserData userData = userDataService.getUserDataByUserId(user.getId());
        return getAdminUserModel(user,userData);
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators/{id}", method = RequestMethod.DELETE)
    public ActionResult delUserAdministrators(@PathVariable("id") long userId
    ) {
        userService.delUser(userId);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/admin/user/{id}/enabled", method = RequestMethod.PUT)
    public ActionResult setUserEnabled(@PathVariable("id") long id, @JsonPathArg("enabled") boolean enabled) throws Exception {
        userService.setUserStatus(id, enabled);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.GET)
    public UserCommonModel getAdminUserCommonById(@PathVariable("user_id") long userId) {
        User user = userService.getUserByUserId(userId);
     //   Map<Integer, Area> allAreaMap = areaService.getAreaMapFromAllArea();
        UserCommonModel userCommonModel = new UserCommonModel();

        userCommonModel.setUser_id(user.getId());
        userCommonModel.setAvatar(user.getAvatar());
        userCommonModel.setNick_name(user.getNickname());
        userCommonModel.setPhone(user.getMobile());
        userCommonModel.setCity_id(user.getAreaId());
        userCommonModel.setName(user.getRealName());
        userCommonModel.setAddress(user.getAddress());
        userCommonModel.setCreated(DateUtil.formatTime(user.getCreatedOn()));
        userCommonModel.setCity_name(countryService.getAreaName(user.getAreaId(),LanguageEnum.values()[user.getUserLanguage()]));
        userCommonModel.setStatus(user.getUserStatus().ordinal());
        userCommonModel.setSignature(user.getSignature());


        return userCommonModel;
    }

    @AuthRequired
    @RequestMapping(value = "/admin/administrators/{id}/enabled", method = RequestMethod.PUT)
    public ActionResult setAdministratorsEnabled(@PathVariable("id") long id, @JsonPathArg("enabled") boolean enabled) throws Exception {
        userService.setUserStatus(id, enabled);
        return new ActionResult();
    }


    /**
     * 账号列表
     */
    @AuthRequired
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public PagedListModel<List<Map<String, Object>>> getAdminUsers(@RequestParam(value = "p", required = false) Integer page,
                                                                   @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                                   @RequestParam(value = "type", required = false) Integer type,
                                                                   @RequestParam(value = "state", required = false) Integer state,
                                                                   @RequestParam(value = "keyword", required = false) String keyword) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        UserStatusEnum userState = state == null ? null : UserStatusEnum.values()[state];

        page = page == null ? 0 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<User> users = userService.getUserByUserType(UserType.USER, page, pageSize);
        int total = userService.getUserTotalNum(UserType.USER);
        List<AdminUserModel> result = new ArrayList<>();
        for (User user : users) {
            UserData userData = userDataService.getUserDataByUserId(user.getId());
            result.add(getAdminUserModel(user,userData));
        }
        return new PagedListModel(result, total, page, pageSize);
    }


//    @AuthRequired
//    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.GET)
//    public AdminUserModel getAdminUserByUserId(@PathVariable("user_id") long userId) {
//        User user = userService.getUserByUserId(userId);
//        if (user == null) {
//            throw new RuntimeException("用户信息不存在！！！");
//        }
//
//        return getAdminUserModel(user);
//    }

    @AuthRequired
    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.PUT)
    public ActionResult updateAdminUser(@PathVariable("user_id") long userId,
                                        @JsonPathArg("state") int state,
                                        @JsonPathArg("remark") String remark,
                                        @JsonPathArg("group") long groupId,
                                        @Value("#{request.getAttribute('lang')}") String lang,
                                        @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        User user = userService.getUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException(i18nService.getMessage("" + 111,lang));
        }
//        user.setUserStatus(UserStatusEnum.values()[state]);
//        user.setInvestorGroupId(groupId);
//        user.setRemark(remark);
//        userService.updateUser(user);
//
//        InvestorGroup investorGroup = investorGroupService.getInvestorGroupById(groupId);
//        //发送通知
//        noticeService.sendNotice(NoticeService.sendNoticeType.noticeChangeInvestorGroup,currentUser.getId(),investorGroup==null?"":investorGroup.getName(),null);
//
//        if(UserStatusEnum.EFFECTIVE == UserStatusEnum.values()[state]){
//            if(UserType.INVESTOR == user.getUserType()){
//                sendSMS.sendKechagnhuhui(user.getMobile(),"SMS_16350324");
//            }else if(UserType.COMPANY_ADMIN == user.getUserType()){
//                sendSMS.sendKechagnhuhui(user.getMobile(),"SMS_16300239");
//            }
//
//        }


        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/admin/users/{user_id}/disabled", method = RequestMethod.PUT)
    public Map<String, Object> updateAdminDisabled(@PathVariable("user_id") long userId,
                                                   @Value("#{request.getAttribute('lang')}") String lang,
                                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        User user = userService.getUserByUserId(userId);
        if (user == null) {
            throw new RuntimeException(i18nService.getMessage("" + 111,lang));
        }
        UserStatusEnum userStatusEnum = null;
        if (user.getUserStatus() == UserStatusEnum.INVALID || user.getUserStatus() == UserStatusEnum.EFFECTIVE) {
            if (user.getUserStatus() == UserStatusEnum.INVALID) {
                userStatusEnum = UserStatusEnum.EFFECTIVE;
            } else if (user.getUserStatus() == UserStatusEnum.EFFECTIVE) {
                userStatusEnum = UserStatusEnum.INVALID;
            }
            user.setUserStatus(userStatusEnum);
            userService.updateUser(user);
            Map<String, Object> result = new HashedMap();
            result.put("state", userStatusEnum.ordinal());
            return result;
        }
        return new HashedMap();
    }


    @AuthRequired
    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.DELETE)
    public ActionResult delAdminUser(@PathVariable("user_id") long userId) {
        userService.delUser(userId);
        return new ActionResult();
    }



    /**
     * 获得国家简码
     */
    @RequestMapping(value = "/account/conutry_code", method = RequestMethod.GET)
    public PagedListModel<List<Map<String, Object>>> getCountryCodes(@RequestParam(value = "p", required = false) Integer page,
                                                                   @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                                     @Value("#{request.getAttribute('lang')}") String lang
                                                                   ) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        List<CountryCode> countryCodes = countryService.getCountryCodes(page,pageSize);
        List<Map<String, Object>> result = new ArrayList<>();
        for (CountryCode countryCode : countryCodes) {
            Map<String,Object> map = new HashMap<>();
            if(LanguageEnum.en.name().equals(lang)){
                map.put("name",countryCode.getCountryEn());
            }else {
                map.put("name",countryCode.getCountryCn());
            }
            map.put("code",countryCode.getCountryCode());
            result.add(map);
        }
        return new PagedListModel(result, 214, page, pageSize);
    }









    //获得客户端真实IP地址的方法二：
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private AdminUserModel getAdminUserModel(User user, UserData userData) {
        AdminUserModel adminUserModel = new AdminUserModel();
        adminUserModel.setCreated(DateUtil.formatTime(user.getCreatedOn()));
        adminUserModel.setEnabled(user.getUserStatus() == UserStatusEnum.INVALID ? false : true);
        adminUserModel.setId(user.getId());
        adminUserModel.setLast_login_at(DateUtil.formatTime(user.getUpdatedOn()));
        adminUserModel.setLogin_count(user.getLonginNum());
        adminUserModel.setLast_login_ip(user.getLonginIp());
        adminUserModel.setUsername(user.getUsername());
        adminUserModel.setPhone_number(user.getMobile());
        adminUserModel.setReal_name(user.getRealName());
        adminUserModel.setNick_name(user.getNickname());
        adminUserModel.setBirth(userData.getBirthday() == null ? "" : DateUtil.format(userData.getBirthday(), DateUtil.C_DATE_PATTON_DEFAULT));
        adminUserModel.setSex(userData.getSex().ordinal());
        adminUserModel.setAge(getAgeByBirth(userData.getBirthday()));
        adminUserModel.setCountry_code(user.getCountryCode());
        return adminUserModel;
    }


    private int getAgeByBirth(Date birthday) {
        if(birthday ==null){
            return 0;
        }

        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}
