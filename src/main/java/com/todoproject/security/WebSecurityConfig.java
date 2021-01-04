package com.todoproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        public App1ConfigurationAdapter() {
            super();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

                http.authorizeRequests().antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                        .antMatchers("/add_todo/").hasAuthority( "USER").antMatchers("/update_todo")
                        .hasAuthority( "USER").antMatchers("/delete_todo/").hasAuthority("USER")
                        .antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").
                        usernameParameter("username").passwordParameter("password").permitAll()
                        .and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/403");

            }

        }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App2ConfigurationAdapter() {
            super();
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                    .antMatchers("/user").hasAuthority( "ADMIN").antMatchers("/admin").hasAuthority("ADMIN")
                    .antMatchers("/list_user/").hasAuthority("ADMIN").antMatchers("/add_user/").hasAnyAuthority( "ADMIN")
                    .antMatchers("/update_user/").hasAuthority( "ADMIN").antMatchers("/delete_user/").hasAuthority("ADMIN")
                    .antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").
                    usernameParameter("username").passwordParameter("password").permitAll()
                    .and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/403");

        }
    }
    }


