package com.revature.pantry.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{
        //TODO sort out what requests to allow, allowing all for now
        http.csrf().disable().authorizeRequests()
                .antMatchers("/**")
                .permitAll();

        //Allow H2-Console to render X-Frame-Options
        http.headers().frameOptions().sameOrigin();
    }
}
