package com.kuyuntech.hapmonitor.platform.configuration.core;

import com.kuyuntech.hapmonitor.platform.interceptor.core.AuthAdminInterceptor;
import com.kuyuntech.hapmonitor.platform.interceptor.core.AuthUserInterceptor;
import com.kuyuntech.hapmonitor.platform.interceptor.core.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthAdminInterceptor authAdminInterceptor() {
        return new AuthAdminInterceptor();
    }

    @Bean
    public AuthUserInterceptor authUserInterceptor() {
        return new AuthUserInterceptor();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authAdminInterceptor())
//                .addPathPatterns("/umsAdmin/**")
//                .excludePathPatterns("/umsAdmin/login", "/umsAdmin/register", "/umsAdmin/logout");
        registry.addInterceptor(authUserInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/umsUser/login", "/umsUser/register", "/umsUser/logout", "/umsResources/list", "/auth/verifyCode");
    }
}
