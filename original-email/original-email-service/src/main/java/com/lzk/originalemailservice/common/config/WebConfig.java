package com.lzk.originalemailservice.common.config;

import com.lzk.originalusercommon.filter.TokenFilterRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

//    @Bean
//    public FilterRegistrationBean tokenFilter(){
//        return new TokenFilterRegistrationBean(redisTemplate,10, new ArrayList<>(), "/*");
//    }

    /**
     * 拦截器配置
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//    }

    /**
     * 路径映射（页面跳转）
     * @param registry
     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//    }

    /**
     * 自定义静态资源映射目录
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//    }


}
