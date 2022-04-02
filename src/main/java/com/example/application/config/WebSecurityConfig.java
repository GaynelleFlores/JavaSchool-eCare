package com.example.application.config;

import com.example.application.services.ClientsService;
import com.example.application.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientsService clientsService;

    @Bean
    public UserDetailsService UserDetailsService() {
        return new UserDetailsServiceImpl(clientsService);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl(clientsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.formLogin().loginPage("/login").usernameParameter("login")
                .passwordParameter("pass")
                .loginProcessingUrl("/doLogin")
                .failureUrl("/login?error=true");

        http.logout()
                .logoutUrl("/doLogout")
                .logoutSuccessUrl("/login");

        http.authorizeRequests()
                .antMatchers("/manager/mainPage", "/contracts", "/clients", "/plans", "/options", "/createContract","/createClient", "/createOption", "/createPlan", "/clients/**/edit", "/plans/**/edit", "/options/**/edit", "/contracts/**/editByManager").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/account/**", "/contracts/**/edit").access("hasRole('ROLE_USER')")
                .anyRequest().permitAll()
                .and().formLogin().defaultSuccessUrl("/default", true);
    }
}