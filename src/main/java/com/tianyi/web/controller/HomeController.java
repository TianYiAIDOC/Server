package com.tianyi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final RequestMappingHandlerMapping handlerMapping;
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    public HomeController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Map<String, String>> index() {
        List<Map<String, String>> requests = new ArrayList<Map<String, String>>();
        Map<RequestMappingInfo, HandlerMethod> tmp = this.handlerMapping.getHandlerMethods();
        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> it = tmp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<RequestMappingInfo, HandlerMethod> entry = it.next();
            RequestMappingInfo key = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Map<String, String> req = new HashMap<String, String>();
            // req.put("name", handlerMethod.getBeanType().getSimpleName() + "_" + handlerMethod.getMethod().getName());
            req.put("url", key.getPatternsCondition().toString());
            req.put("method", key.getMethodsCondition().toString());
            requests.add(req);
        }
        return requests;
    }


}
