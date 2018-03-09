package com.tianyi.web.controller;

import com.tianyi.bo.User;
import com.tianyi.bo.UserCase;
import com.tianyi.service.I18nService;
import com.tianyi.service.UserCaseService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.util.JsonPathArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/3.
 */
@RestController
public class UserCaseController {



    @Autowired
    UserCaseService userCaseService;

    @Autowired
    I18nService i18nService;

    @AuthRequired
    @RequestMapping(value = "/usercase", method = RequestMethod.GET)
    public List<UserCase> caselist(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                   @Value("#{request.getAttribute('lang')}") String lang) {

        return userCaseService.getUserCaseByUserId(currentUser.getId());
    }

    //@AuthRequired
    @RequestMapping(value = "/usercase/{caseid}", method = RequestMethod.GET)
    public UserCase caselist(@PathVariable("caseid") long caseid,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser,
                                   @Value("#{request.getAttribute('lang')}") String lang) {

        UserCase userCase = userCaseService.getById(caseid);
        userCase.getVisitReasonImg();
        userCase.getVisitResultImg();
        userCase.getVisitRepostImg();
        userCase.getCasePhotoImg();
        userCase.getDoctorAdviceImg();
        userCase.getDoctorInfoImg();


        return userCase;
    }

    @AuthRequired
    @RequestMapping(value = "/usercase/{caseid}", method = RequestMethod.DELETE)
    public void deleteCase(@PathVariable("caseid") long caseid,
                             @Value("#{request.getAttribute('currentUser')}") User currentUser,
                             @Value("#{request.getAttribute('lang')}") String lang) {

         userCaseService.deleteById(caseid);
    }



    @AuthRequired
    @RequestMapping(value = "/usercase", method = RequestMethod.PUT)
    public ActionResult save(@JsonPathArg("visitDay") Date visitDay,
                             @JsonPathArg("visitType") int visitType,
                             @JsonPathArg("visitHospial") String visitHospial,
                             @JsonPathArg("visitDepartment") String visitDepartment,
                             @JsonPathArg("doctorName") String doctorName,
                             @JsonPathArg("visitFee") float visitFee,
                             @JsonPathArg("visitReason") String visitReason,
                             @JsonPathArg("visitResult") String visitResult,
                             @JsonPathArg("doctorAdvice") String doctorAdvice,
                             @JsonPathArg("doctorInfo") String doctorInfo,
                             @JsonPathArg("visitRepost") String visitRepost,
                             @JsonPathArg("attachFileKeyJson") String attachFileKeyJson,
                             @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        UserCase userCase = new UserCase();
        userCase.setUserId(currentUser.getId());
        userCase.setVisitDay(visitDay);
        userCase.setVisitType(visitType);
        userCase.setVisitHospial(visitHospial);
        userCase.setVisitDepartment(visitDepartment);
        userCase.setDoctorName(doctorName);
        userCase.setVisitFee(visitFee);
        userCase.setVisitResult(visitResult);
        userCase.setVisitReason(visitReason);
        userCase.setDoctorAdvice(doctorAdvice);
        userCase.setVisitRepost(visitRepost);
        userCase.setDoctorInfo(doctorInfo);
        userCase.setAttachFileKeyJson(attachFileKeyJson);
        userCaseService.add(userCase);

        return new ActionResult();
    }
}
