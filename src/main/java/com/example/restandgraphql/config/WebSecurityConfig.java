package com.example.restandgraphql.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/account", "/account/**").hasRole("user")
                .antMatchers("/account-admin",  "/account-admin/**").hasRole("admin")
                .antMatchers("/*", "/graphql*", "/playground*", "/voyager*", "/altair*", "/vendor/**").permitAll()
                .anyRequest().denyAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("TestUser")
                .password(bCryptPasswordEncoder.encode("5G69a"))
                .roles("user")
                .and()
                .withUser("TestAdmin")
                .password(bCryptPasswordEncoder.encode("4H$42"))
                .roles("admin");
    }

}