package com.mkolongo.product_shop.config;

import com.mkolongo.product_shop.domain.JwtPropertiesConfig;
import com.mkolongo.product_shop.services.SellerService;
import com.mkolongo.product_shop.services.UserService;
import com.mkolongo.product_shop.web.filters.JwtAuthenticationFilter;
import com.mkolongo.product_shop.web.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Order(1)
    @Configuration
    @RequiredArgsConstructor
    public static class UserSecurityConfig extends WebSecurityConfigurerAdapter {

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

                    .antMatcher("/users*")
                    .authorizeRequests()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/users/register","/users/login", "/").anonymous()
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginPage("/users/login")
                    .defaultSuccessUrl("/users/home")

                    .and()
                    .logout();

        }
    }

    @Order(2)
    @Configuration
    @RequiredArgsConstructor
    public static class SellerSecurityConfig extends WebSecurityConfigurerAdapter {

        private final JwtPropertiesConfig jwtPropertiesConfig;
        private final SellerService sellerService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(sellerService);
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

                    .antMatcher("/sellers*")
                    .authorizeRequests()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/sellers/register", "sellers/login", "/").anonymous()
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()
                    .loginPage("/sellers/login")
                    .usernameParameter("businessName")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/users/home")

                    .and()
                    .logout();

        }
    }

    private static CsrfTokenRepository csrfTokenRepository() {
        var httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        httpSessionCsrfTokenRepository.setSessionAttributeName("_csrf");
        return httpSessionCsrfTokenRepository;
    }
}
