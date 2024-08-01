package com.digs.dig0.config;

import com.digs.dig0.interceptor.LoggerInterceptor;
import com.digs.dig0.interceptor.SessionTimerInterceptor;
import com.digs.dig0.interceptor.UserInterceptor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@EnableWebMvc
@Configuration
@ComponentScan(value = "com.digs.dig0")
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/anonymous.html");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/index").setViewName("home");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/registers").setViewName("register");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/anonymous.html");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/register/register").setViewName("register");
        registry.addViewController("/register/forgot-password-request").setViewName("forgot-password-form");
        registry.addViewController("/register/password-reset-form").setViewName("password-reset-form");
        registry.addViewController("/event_img").setViewName("event_img");
        registry.addViewController("/flyer").setViewName("flyer");

        registry.addViewController("/event").setViewName("event");
        registry.addViewController("/pricing").setViewName("pricing");
        registry.addViewController("/portfolio-overview").setViewName("portfolio-overview");
        registry.addViewController("/portfolio-item").setViewName("portfolio-item");
        registry.addViewController("/faq").setViewName("faq");
        registry.addViewController("/contact").setViewName("contact");
        registry.addViewController("/blog-post").setViewName("blog-post");
        registry.addViewController("/blog-home").setViewName("blog-home");
        registry.addViewController("/about").setViewName("about");

    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/resources/templates/");
        bean.setSuffix(".html");
        return bean;
    }

    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("templates/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        secondaryTemplateResolver.setOrder(1);
        secondaryTemplateResolver.setCheckExistence(true);

        return secondaryTemplateResolver;
    }

    @SneakyThrows
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(new LoggerInterceptor());
        registry.addInterceptor(new UserInterceptor());
        registry.addInterceptor(new SessionTimerInterceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(byteArrayHttpMessageConverter());
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        list.add(MediaType.APPLICATION_FORM_URLENCODED);
        list.add(MediaType.APPLICATION_JSON);
        list.add(MediaType.MULTIPART_FORM_DATA);
        list.add(MediaType.MULTIPART_MIXED);
        return list;
    }

}
