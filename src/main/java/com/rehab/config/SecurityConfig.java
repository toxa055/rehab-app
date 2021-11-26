package com.rehab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class that configures Spring WebSecurity.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Interface that retrieves user-related data.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructs current instance and initializes following fields.
     *
     * @param userDetailsService description of userDetailsService is in field declaration.
     */
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Set authentication based on UserDetailsService.
     *
     * @param auth implementation of SecurityBuilder.
     * @throws Exception when error occurs.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * Configures web based security for specific http requests.
     *
     * @param http parameter to configure http requests.
     * @throws Exception when error occurs.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll().failureUrl("/login?error=true")
                .and()
                .logout().permitAll().logoutSuccessUrl("/login");
    }

    /**
     * Method that initializes a new instance of PasswordEncoder's implementation.
     * It's used for encoding users passwords not to keep raw passwords in database.
     *
     * @return new instance of BCryptPasswordEncoder with strength 12;
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
