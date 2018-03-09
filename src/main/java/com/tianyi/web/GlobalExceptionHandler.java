package com.tianyi.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingqingwan on 12/28/15
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Map<String, Object> handleUnauthorizedException(HttpServletRequest request, Exception ex) {
        LOGGER.error("error", ex);

        Map<String, Object> results = new HashMap<>();
        results.put("err_code", 405);
        results.put("err_msg", ex.getMessage());
        ex.printStackTrace();
        return results;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, Object> handleException(HttpServletRequest request, Exception ex) {
        LOGGER.error("error", ex);

        Map<String, Object> results = new HashMap<>();
        results.put("err_code", 500);
        results.put("err_msg", ex.getMessage());
        ex.printStackTrace();
        return results;
    }



}
