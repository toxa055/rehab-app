package com.rehab.config;

import com.rehab.model.type.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/employees").hasAnyRole("ADMIN")
                .antMatchers("/patients/**").hasAnyRole("ADMIN", "DOCTOR", "NURSE")
                .antMatchers("/treatments/**").hasAnyRole("ADMIN", "DOCTOR")
                .antMatchers("/prescriptions/**").hasAnyRole("ADMIN", "DOCTOR")
                .antMatchers("/events/**").hasAnyRole("ADMIN", "NURSE")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("doctor")
                        .password(passwordEncoder().encode("doctor"))
                        .roles(Role.DOCTOR.name())
                        .build(),
                User.builder()
                        .username("nurse")
                        .password(passwordEncoder().encode("nurse"))
                        .roles(Role.NURSE.name())
                        .build()

        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
