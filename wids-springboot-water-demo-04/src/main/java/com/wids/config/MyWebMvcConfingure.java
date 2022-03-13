package com.wids.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 被@Configuration注解修饰的类是Spring容器的配置类，等价于xml配置文件
 * WebMvcConfigurer接口是SpringBoot开发Web应用的配置器
 */
@Configuration
public class MyWebMvcConfingure implements WebMvcConfigurer {

    /**
     * 将LoginHandlerInterceptor拦截器注入到Spring容器中，Web服务器拦截所有路径，但是会排除以下资源
     *
     * 登录路径不拦截："/","/index.html","/login"
     * 错误路径不拦截："/error/**"
     * 静态路径不拦截："/css/**","/fonts/**","/images/**"
     */
    private static final String[] INTERCEPTOR_PATH ={"/","/index.html","/login",
            "/error/**","/css/**","/fonts/**","/images/**"};

    /**
     * 添加一个视图控制器的映射，将/index.html请求路径映射到对应的页面中(/templates/water/index.html)
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/index.html") 表示添加一个控制器，该控制器主要用来处理/index.html请求路径。
        // .setViewName("index")将/index.html请求路径映射到对应的视图中(/templates/water/index.html)
        registry.addViewController("/index.html").setViewName("index");
    }

    /**
     * 将创建的拦截器对象LoginHandlerInterceptor注入到Spring容器中，
     * 且排除以下路径："/","/index.html","/login",
     *             "/error/**","/css/**","/fonts/**","/images/**"
     * @param registry 拦截器注册器对象
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).excludePathPatterns(INTERCEPTOR_PATH);
    }

}
