package com.tianyi.web;

import com.tianyi.bo.User;
import com.tianyi.service.I18nService;
import com.tianyi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lingqingwan on 12/29/15
 */
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Resource
    UserService userService;
    @Resource
    I18nService i18nService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        //httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        //httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        //httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,X-Token,X-App-Name ");

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(AuthRequired.class);

            String language = httpServletRequest.getHeader("X-LANG");
            String lang = "zh";
            if(language!=null&&StringUtils.isNotEmpty(language)&&"1".equals(language)){
                lang = "en";
            }

            httpServletRequest.setAttribute("lang", lang);

            if (authRequired == null) {
                return true;
            }

            String token = httpServletRequest.getHeader("X-Token");

            if (StringUtils.isBlank(token)) {
                token = httpServletRequest.getParameter("token");
            }
            if (StringUtils.isBlank(token)) {
                throw new UnauthorizedException(i18nService.getMessage("" + 109,lang));
            }


            User user = userService.getUserByToken(token);
            if (user == null) {
                throw new UnauthorizedException(i18nService.getMessage("" + 109,lang));
            }

            httpServletRequest.setAttribute("currentUser", user);

            if(user.getUserLanguage() == 1){
                lang = "en";
            }
            httpServletRequest.setAttribute("lang", lang);

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
