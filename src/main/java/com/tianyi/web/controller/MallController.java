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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/3.
 */
@RestController
public class MallController {



    @Autowired
    UserCaseService userCaseService;

    @Autowired
    I18nService i18nService;

    @AuthRequired
    @RequestMapping(value = "/mall", method = RequestMethod.GET)
    public Map<String,Object> caselist(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                       @Value("#{request.getAttribute('lang')}") String lang) {

        Map<String,Object> result = new HashMap();

        if(!"en".equals(lang)){
            result.put("url","http://api.yidiyitao.com/h5-app/");
        }
        else{
            result.put("url","http://api.yidiyitao.com/h5-app-en/");
        }
        return result;
    }
}
