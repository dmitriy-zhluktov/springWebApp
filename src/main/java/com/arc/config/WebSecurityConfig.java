package com.arc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/") // страница логина
                .loginProcessingUrl("/login") // POST-url
                .usernameParameter("user")  // поле с логином, по умолчанию username
                .passwordParameter("passwd")  // поле с логином, по умолчанию password
                .successHandler((req, res, auth) -> {    // в случае успеха
                    for (GrantedAuthority authority : auth.getAuthorities()) {
                        System.out.println(authority.getAuthority());
                    }
                    System.out.println(auth.getName());
                    req.setAttribute("message", "You are successfully login!");
                    res.sendRedirect("/admin");
                })
                //.defaultSuccessUrl("/admin")   //  используется, если отсутствует successHandler
                .failureHandler((req, res, exp) -> {  // в случае неудачи
                    String errMsg = "";
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)){
                        errMsg = "Invalid username or password.";
                    } else {
                        errMsg = "Unknown error - " + exp.getMessage();
                    }
                    req.getSession().setAttribute("error", errMsg);
                    res.sendRedirect("/");
                })
                //.failureUrl("/index?error")   // используется, если отсутствует failureHandler
                .permitAll() // пока непонятно Allow access to any URL associate to formLogin()
                .and()
                .logout()
                .logoutUrl("/signout")   // страница выхода, по умолчанию logout
                .logoutSuccessHandler((req, res, auth) -> {   // в случае успеха
                    req.getSession().setAttribute("message", "You are logged out successfully.");
                    res.sendRedirect("/");
                })
                //.logoutSuccessUrl("/login") // используется, если отсутствует logoutSuccessHandler
                .permitAll() // пока неясно Allow access to any URL associate to logout()
                .and()
                .csrf().disable(); // пока неясно, тут какой-то токен Disable CSRF support
    }
}
