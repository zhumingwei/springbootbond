package com.zhumingwei.bond.config;

import com.zhumingwei.bond.Constant;
import com.zhumingwei.bond.interception.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.zhumingwei.bond.ConstantKt.LOGIN_URL;

/**
 * @author zhumingwei
 * @date 2018/6/18 下午5:51
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(LOGIN_URL);
        super.addInterceptors(registry);
    }
}
