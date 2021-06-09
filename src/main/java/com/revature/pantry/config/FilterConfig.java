package com.revature.pantry.config;

import com.revature.pantry.web.filters.AuthFilter;
import com.revature.pantry.web.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class FilterConfig {

    @Bean
    @SuppressWarnings({"rawtypes, unchecked"})
    public FilterRegistrationBean authRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return  filterRegistrationBean;
    }

    @Bean
    @SuppressWarnings({"rawtypes, unchecked"})
    public FilterRegistrationBean corsRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
        return filterRegistrationBean;
    }
}
