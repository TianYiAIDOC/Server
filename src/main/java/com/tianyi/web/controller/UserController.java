package com.tianyi.web.controller;

import com.tianyi.bo.User;
import com.tianyi.bo.UserData;
import com.tianyi.bo.UserDataResult;
import com.tianyi.bo.UserDayData;
import com.tianyi.bo.enums.BirthStatusEnum;
import com.tianyi.bo.enums.MaritalStatusEunm;
import com.tianyi.bo.enums.SexEnum;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.service.*;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.*;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.JsonPathArg;
import com.tianyi.web.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by 雪峰 on 2018/1/3.
 */
@RestController
public class UserController {



    @Autowired
    UserDayDataService userDayDataService;
    @Autowired
    UserDataService userDataService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    I18nService i18nService;

    @AuthRequired
    @RequestMapping(value = "/user/sign", method = RequestMethod.PUT)
    public ActionResult userSifn(@Value("#{request.getAttribute('currentUser')}") User currentUser,@Value("#{request.getAttribute('lang')}") String lang) {

        UserDayData userDayData = userDayDataService.getUserDayDataByDay(currentUser.getId(), UserDayEnum.SIGN, DateUtil.format(DateUtil.getCurrentDate(),DateUtil.C_DATE_PATTON_DEFAULT));
        if(userDayData !=null){
            throw new RuntimeException(i18nService.getMessage("" + 115,lang));
        }
        userDayDataService.saveEveryDayData(currentUser.getId(),UserDayEnum.SIGN,1,"");
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/user/getSigns", method = RequestMethod.GET)
    public PagedListModel<List<Map<String,Object>>> getUserSigns(@RequestParam(value = "yearMonth", required = false) String yearMonth,
                                     @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        if(yearMonth ==null || StringUtils.isEmpty(yearMonth)){
            yearMonth = DateUtil.format(new Date(),"yyyy-MM");
        }

        List<UserDayData> userDayDataList = userDayDataService.getUserDayDatasByMotnth(currentUser.getId(),UserDayEnum.SIGN,yearMonth);

        List<Map<String,Object>> result = new ArrayList<>();
        for(UserDayData userDayData:userDayDataList){
            Map<String,Object> map = new HashMap<>();
            map.put("day",userDayData.getCreatedOn().getDay());
            result.add(map);
        }

        return new PagedListModel(result,result.size(),0,result.size());
    }


    @AuthRequired
    @RequestMapping(value = "/user/stepsNum", method = RequestMethod.PUT)
    public ActionResult stepsNum(@JsonPathArg("steps") Integer steps,
                                 @Value("#{request.getAttribute('currentUser')}") User currentUser,@Value("#{request.getAttribute('lang')}") String lang) {

        if(steps ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }

        if(steps == 0){
            return new ActionResult();
        }


        UserDayData userDayData = userDayDataService.getUserDayDataByDay(currentUser.getId(), UserDayEnum.STEPSNUM,DateUtil.format(DateUtil.getCurrentDate(),DateUtil.C_DATE_PATTON_DEFAULT));
        if(userDayData !=null){
             if(steps > userDayData.getDataNumber()){
                 userDayData.setDataNumber(steps);
                 userDayDataService.updateUserDayDat(userDayData);
             }
            return new ActionResult();
        }else{
            userDayDataService.saveEveryDayData(currentUser.getId(),UserDayEnum.STEPSNUM,steps,"");
        }

        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/user/getSteps", method = RequestMethod.GET)
    public PagedListModel<List<Map<String,Object>>> getUserSteps(@RequestParam(value = "p", required = false) Integer page,
                                                 @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                 @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        page = page == null ? 0 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        List<Map<String,Object>> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for(int i=0;i<pageSize;i++){
            calendar.setTime(DateUtil.getCurrentDate());
            calendar.add(Calendar.DATE, -i*(page==0?1:page));

            UserDayData userDayData = userDayDataService.getUserDayDataByDay(currentUser.getId(), UserDayEnum.STEPSNUM,DateUtil.format(calendar.getTime(),DateUtil.C_DATE_PATTON_DEFAULT));

            Map<String,Object> map = new HashMap<>();
            map.put("yearMonth",DateUtil.format(calendar.getTime(),DateUtil.C_DATE_PATTON_DEFAULT));
            map.put("steps",userDayData ==null?0:userDayData.getDataNumber());
            map.put("aidoc", Tools.getDecimalFour(Tools.toDoulbe(accountService.getDayMoney(currentUser.getId(),DateUtil.format(calendar.getTime(),DateUtil.C_DATE_PATTON_DEFAULT)))/1000));
            result.add(map);

        }




        return new PagedListModel(result,userDayDataService.getTotalNum(currentUser.getId(),UserDayEnum.STEPSNUM),page,pageSize);
    }


    @AuthRequired
    @RequestMapping(value = "/user/target", method = RequestMethod.PUT)
    public ActionResult userTarget(@JsonPathArg("target") Integer target,
                                 @Value("#{request.getAttribute('currentUser')}") User currentUser,@Value("#{request.getAttribute('lang')}") String lang) {


        if(target ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setTarget(target);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/user/target", method = RequestMethod.GET)
    public Map<String,Object> getUserTarget(@Value("#{request.getAttribute('currentUser')}") User currentUser) {

        Map<String,Object> result = new HashMap<>();

        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        result.put("target",userData.getTarget());

        return result;
    }

    @AuthRequired
    @RequestMapping(value = "/user/archives", method = RequestMethod.GET)
    public UserArchivesModel getUserArchives(@Value("#{request.getAttribute('currentUser')}") User currentUser) {

        UserArchivesModel result = new UserArchivesModel();
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        result.setUserId(currentUser.getId());
        result.setBirthday(userData.getBirthday() ==null?"":DateUtil.format(userData.getBirthday()));
        result.setBirthStatus(userData.getBirthStatus().ordinal());
        result.setSex(userData.getSex().ordinal());
        result.setHeight(userData.getHeight());
        result.setWeight(userData.getWeight());
        result.setDrugAllergy(userData.getDrugAllergy());
        result.setMaritalSttus(userData.getMaritalStatus().ordinal());
        result.setMedicalHistory(userData.getMedicalHistory());
        result.setOtherAllergy(userData.getOtherAllergy());
        result.setOtherHabits(userData.getOtherHabits());
        result.setPersonalHabits(userData.getPersonalHabits());
        result.setSurgicalTrauma(userData.getSurgicalTrauma());
        return result;
    }



    @AuthRequired
    @RequestMapping(value = "/user/archives", method = RequestMethod.PUT)
    public ActionResult saveUserArchives( @JsonPathArg("birthday") Date birthday,
                                               @JsonPathArg("height") int height,
                                               @JsonPathArg("weight") int weight,
                                               @JsonPathArg("sex") int sex,
                                               @JsonPathArg("maritalSttus") int maritalSttus,
                                               @JsonPathArg("birthStatus") int birthStatus,
                                               @JsonPathArg("surgicalTrauma") String surgicalTrauma,
                                               @JsonPathArg("medicalHistory") String medicalHistory,
                                               @JsonPathArg("drugAllergy") String drugAllergy,
                                               @JsonPathArg("otherAllergy") String otherAllergy,
                                               @JsonPathArg("personalHabits") String personalHabits,
                                               @JsonPathArg("otherHabits") String otherHabits,
                                                @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());

        userData.setBirthday(birthday);
        userData.setBirthStatus(BirthStatusEnum.values()[birthStatus]);
        userData.setSex(SexEnum.values()[sex]);
        userData.setHeight(height);
        userData.setWeight(weight);
        userData.setDrugAllergy(drugAllergy);
        userData.setMaritalStatus(MaritalStatusEunm.values()[maritalSttus]);
        userData.setMedicalHistory(medicalHistory);
        userData.setOtherAllergy(otherAllergy);
        userData.setOtherHabits(otherHabits);
        userData.setPersonalHabits(personalHabits);
        userData.setSurgicalTrauma(surgicalTrauma);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/user/sports", method = RequestMethod.GET)
    public UserSportsModel getUserSports(@Value("#{request.getAttribute('currentUser')}") User currentUser) {

        UserSportsModel result = new UserSportsModel();
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        result.setShape(userData.getShape());
        result.setSportDay(userData.getSportDay());
        result.setSportsFieild(userData.getSportsFieild());
        result.setSportsGoal(userData.getSportsGoal());
        result.setSportsHour(userData.getSportsHour());
        result.setSportsLiked(userData.getSportsLiked());
        result.setSportsState(userData.getSportsState());
        result.setWeightInfo(userData.getWeightInfo());
        result.setWristLength(userData.getWristLength());
        return result;
    }

    @AuthRequired
    @RequestMapping(value = "/user/sportsState", method = RequestMethod.PUT)
    public ActionResult sportsState(@JsonPathArg("sportsState") Integer sportsState,
                                    @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportsState ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportsState(sportsState);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/user/sportsGoal", method = RequestMethod.PUT)
    public ActionResult sportsGoal(@JsonPathArg("sportsGoal") Integer sportsGoal,
                                   @Value("#{request.getAttribute('lang')}") String lang,
                                    @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportsGoal ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportsGoal(sportsGoal);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/user/sportsLiked", method = RequestMethod.PUT)
    public ActionResult sportsGoal(@JsonPathArg("sportsLiked") String sportsLiked,
                                   @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportsLiked ==null||StringUtils.isEmpty(sportsLiked)){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportsLiked(sportsLiked);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/user/sportsFieild", method = RequestMethod.PUT)
    public ActionResult sportsFieild(@JsonPathArg("sportsFieild") String sportsFieild,
                                     @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportsFieild ==null||StringUtils.isEmpty(sportsFieild)){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportsFieild(sportsFieild);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/user/sportDay", method = RequestMethod.PUT)
    public ActionResult sportDay(@JsonPathArg("sportDay") Integer sportDay,
                                 @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportDay ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportDay(sportDay);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/user/sportsHour", method = RequestMethod.PUT)
    public ActionResult sportsHour(@JsonPathArg("sportsHour") Integer sportsHour,
                                   @Value("#{request.getAttribute('lang')}") String lang,
                                 @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(sportsHour ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSportsHour(sportsHour);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/user/weightInfo", method = RequestMethod.PUT)
    public ActionResult weightInfo(@JsonPathArg("weightInfo") Integer weightInfo,
                                   @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(weightInfo ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setWeightInfo(weightInfo);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/user/shape", method = RequestMethod.PUT)
    public ActionResult shape(@JsonPathArg("shape") Integer shape,
                              @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(shape ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setShape(shape);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/user/wristLength", method = RequestMethod.PUT)
    public ActionResult wristLength(@JsonPathArg("wristLength") Integer wristLength,
                                    @Value("#{request.getAttribute('lang')}") String lang,
                              @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(wristLength ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setWristLength(wristLength);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }


//    @AuthRequired
    @RequestMapping(value = "/user/attribute", method = RequestMethod.GET)
    public Map<String,Object> getUserAttribute(@RequestParam(value = "userId", required = false) Long userId,
                                               @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        Map<String,Object> result = new HashMap<>();

        User user = currentUser;

        if(userId !=null){
            user = userService.getUserByUserId(userId);
        }

        UserData userData = userDataService.getUserDataByUserId(user.getId());
        result.put("nickname",user.getNickname());
        result.put("avatar",user.getAvatar());
        result.put("signature",user.getSignature());
        result.put("sex",userData.getSex().ordinal());
        result.put("birthday",userData.getBirthday()==null?"":DateUtil.format(userData.getBirthday(),DateUtil.C_DATE_PATTON_DEFAULT));


        return result;

    }



    @AuthRequired
    @RequestMapping(value = "/user/birthday", method = RequestMethod.PUT)
    public ActionResult editUserbirthday( @JsonPathArg("birthday") Date birthday,
                                          @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setBirthday(birthday);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/user/sex", method = RequestMethod.PUT)
    public ActionResult editUserSex(@JsonPathArg("sex") int sex,
                                          @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        UserData userData = userDataService.getUserDataByUserId(currentUser.getId());
        userData.setSex(SexEnum.values()[sex]);
        userDataService.updateUserData(userData);
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/user/language", method = RequestMethod.PUT)
    public ActionResult userLang(@JsonPathArg("language") Integer language,
                                 @Value("#{request.getAttribute('lang')}") String lang,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(language ==null){
            throw new RuntimeException(i18nService.getMessage("" + 116,lang));
        }
        User user = userService.getUserByUserId(currentUser.getId());
        user.setUserLanguage(language);
        userService.updateUser(user);
        return new ActionResult();
    }

    //@AuthRequired
    @RequestMapping(value = "/user/ranking", method = RequestMethod.GET)
    public Map<String,Object> ranking() {


        Map<String,Object> result= new HashMap();

        List<UserDataResult> day = userService.getUserRanking();

        List<UserDataResult> total = userService.getUserTotalRanking();
        result.put("day",day);
        result.put("total",total);


        return result;
    }
}
