/**
 * 成都铁路局科研所
 * <ul>
 * <li>Author: 		焦运磊</li>
 * <li>Date: 		2018年1月19日</li>
 * <li>Description:	 </li>
 * <li>+-History-------------------------------------+</li>
 * <li>| Date		Author		Description	</li>
 * <li>|2018年1月19日     焦运磊     Created</li>
 * <li>+------------------------------------------------</li>
 * </ul>
 */
package com.learn.admin.common.config;

import com.learn.admin.common.interceptor.LoginHandleInterceptor;
import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;

/**
 * 类注释
 *
 * @author lijun
 * @date 2018年1月19日
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("/**")
                .addResourceLocations("/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 静态资源过滤，不进入动态转发逻辑
     */
    @Bean(name="default")
    public ServletRegistrationBean dispatcherRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setServlet(new DefaultServlet());
        registration.addUrlMappings("/static/**");
        return registration;
    }

    /**
     * 设置默认首页
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName( "login" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginHandleInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/asserts/**",
                        "/webjars/**"))
                .excludePathPatterns("/","/login.html");


    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] headers = new String[]{"Content-Type", "Access-Control-Allow-Headers","Authorization", "X-Requested-With"};
        registry.addMapping("/**").allowedHeaders(headers).allowedMethods("POST", "GET", "OPTIONS", "DELETE", "PUT").allowedOrigins("*");
    }


}
