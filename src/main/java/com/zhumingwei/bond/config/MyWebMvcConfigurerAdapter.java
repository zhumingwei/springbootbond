package com.zhumingwei.bond.config;

import com.zhumingwei.bond.interception.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.zhumingwei.bond.ConstantKt.LOGIN_URL;
import static com.zhumingwei.bond.ConstantKt.REGISTER_URL;

/**
 * @author zhumingwei
 * @date 2018/6/18 下午5:51
 */
//todo WebMvcConfigurationSupport 修改   interceptor添加无效
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(LOGIN_URL)
                .excludePathPatterns(REGISTER_URL)
                .excludePathPatterns("/file/gettoken");
        super.addInterceptors(registry);
    }
}
