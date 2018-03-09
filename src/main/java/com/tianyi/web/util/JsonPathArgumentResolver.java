package com.tianyi.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by lingqingwan on 12/28/15
 */
public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    private ObjectMapper om = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonPathArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String jsonBody = getRequestBody(webRequest);
        JsonPathArg jsonPathArg = parameter.getParameterAnnotation(JsonPathArg.class);

        if (!jsonPathArg.optional() && StringUtils.isBlank(jsonBody)) {
            throw new RuntimeException("Json中缺少必填参数:" + jsonPathArg.value());
        }

        JsonNode rootNode = om.readTree(jsonBody);
        JsonNode node = rootNode.path(jsonPathArg.value());

        if (node.getClass() == MissingNode.class && !jsonPathArg.optional()) {
            throw new RuntimeException("Json中缺少必填参数:" + jsonPathArg.value());
        }

        return node.getClass() == MissingNode.class ? null : om.readValue(node.toString(), parameter.getParameterType());
    }


    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        String jsonBody = (String) webRequest.getAttribute(JSONBODYATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getInputStream(), Charset.forName("UTF-8"));
                webRequest.setAttribute(JSONBODYATTRIBUTE, jsonBody, NativeWebRequest.SCOPE_REQUEST);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;

    }

}
