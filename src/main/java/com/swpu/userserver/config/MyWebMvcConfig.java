package com.swpu.userserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MyWebMvcConfig implements WebMvcConfigurer {
    /**
     * addResourceHandler设置映射的url路径
     * addResourceLocations文件实际存放的本地目录
     * @param registry
     */
    @Autowired
    private JWTInterceptor jwtInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/view/**")
                .addResourceLocations("file:///E:\\img\\");
    }

    /**
     * addPathPatterns:表示那些路径使用拦截器
     * excludePathPatterns：表示那些路径不使用拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/vcode","/user/img","/view/**");
    }
}
