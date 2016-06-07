package com.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by kodonghyeon on 2016. 6. 7..
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 토큰 값이 잘못 되면 자동으로 막힘
    http.csrf().disable();

    http.httpBasic();

    // 접근 권한 설정
    http.authorizeRequests()
          .antMatchers(HttpMethod.GET, "/accounts/**").hasRole("USER")
          .antMatchers(HttpMethod.PUT, "/accounts/**").hasRole("USER")
          .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("USER")
          .anyRequest().permitAll();
  }
}
