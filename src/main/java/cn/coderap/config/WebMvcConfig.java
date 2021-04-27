package cn.coderap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created by yw
 * 2021/4/22
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/register","/user/login","/categories","/products","/products/*","/carts","/error");
    }
}
