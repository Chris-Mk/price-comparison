package com.mkolongo.product_shop.config;

import com.mkolongo.product_shop.domain.JwtPropertiesConfig;
import com.mkolongo.product_shop.services.UserService;
import com.mkolongo.product_shop.web.filters.JwtAuthenticationFilter;
import com.mkolongo.product_shop.web.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Order(2)
@Configuration
@RequiredArgsConstructor
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtPropertiesConfig jwtPropertiesConfig;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtPropertiesConfig))
                .addFilterAfter(new JwtTokenFilter(jwtPropertiesConfig), JwtAuthenticationFilter.class)

                .authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/user/register","/user/login", "/").anonymous()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/home", true)

//                .and()
//                .rememberMe()
//                .rememberMeParameter("remember-me")
//                .tokenValiditySeconds(86400)

                .and()
                .logout();

    }

    private static CsrfTokenRepository csrfTokenRepository() {
        var csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfTokenRepository.setSessionAttributeName("_csrf");
        return csrfTokenRepository;
    }
}
