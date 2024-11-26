package com.buyi.config;

import com.buyi.interceptor.AuthorityInterceptor;
import com.buyi.interceptor.AutoLoginInterceptor;
import com.buyi.interceptor.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"com.buyi.controller", "com.buyi.advice", "com.buyi.interceptor"})
public class WebMvcJavaConfig implements WebMvcConfigurer {

    @Resource
    private AutoLoginInterceptor autoLoginInterceptor;
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private AuthorityInterceptor authorityInterceptor;

    // 开启静态资源支持
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**");

        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns(
                        "/student/**",
                        "/apply/page", "/apply/audit",
                        "/train/**"
                )
                .excludePathPatterns(
                        "/student/update", "/student/find",
                        "/train/page", "/train/find/*", "/train/study/*"
                );
    }

}
