package com.tianyi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tianyi.web.logging.LoggingFilter;
import com.tianyi.web.util.JsonPathArgumentResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import java.util.List;

//import org.springframework.boot.context.embedded.FilterRegistrationBean;
//import org.springframework.boot.context.embedded.MultipartConfigFactory;

//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper());
        return mappingJackson2HttpMessageConverter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objMapper;
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setDispatchOptionsRequest(true);
        return servlet;
    }

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/");
        return factory.createMultipartConfig();
    }
    @Bean
    public Filter loggingFilter(){
        return new LoggingFilter(LoggingFilter.Builder.create().excludedPaths("/verify_code/phone"));
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loggingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("loggingFilter");
        return registration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new JsonPathArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor());
    }
}