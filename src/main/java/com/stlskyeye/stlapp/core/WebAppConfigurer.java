package com.stlskyeye.stlapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebAppConfigurer  extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链

        // addPathPatterns 用于添加拦截规则

        // excludePathPatterns 用户排除拦截

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(localInterceptor());
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/page/regist",
                "/page/login",
                "/login/login");
        super.addInterceptors(registry);

    }



}
