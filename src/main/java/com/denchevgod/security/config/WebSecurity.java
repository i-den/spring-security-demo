package com.denchevgod.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth
                .inMemoryAuthentication()
                    .withUser("user")
                    .password(passwordEncoder().encode("pass"))
                    .roles("USER");
    } // @formatter:on

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/users/delete/**").hasAnyRole("ADMIN")
                    .antMatchers("/register").permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()

                .formLogin()
                    .loginPage("/login").permitAll()
                    .loginProcessingUrl("/login")
                    .and()

                .logout().permitAll()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    // .deleteCookies()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout")

                    .and()

                .csrf()
                    .disable()

        ;
    } // @formatter:on



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
