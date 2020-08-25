package com.mkolongo.price_comparison.config;

import com.mkolongo.price_comparison.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Order(1)
@Configuration
@RequiredArgsConstructor
public class SellerSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final JwtPropertiesConfig jwtPropertiesConfig;
    private final SellerService sellerService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sellerService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .antMatcher("/seller/**")
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())

                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtPropertiesConfig))
//                .addFilterAfter(new JwtTokenFilter(jwtPropertiesConfig), JwtAuthenticationFilter.class)

                .authorizeRequests()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/seller/register", "/seller/login").anonymous()
                .anyRequest().hasRole("SELLER")

                .and()
                .formLogin()
                .usernameParameter("businessName")
                .passwordParameter("password")
                .loginPage("/seller/login")
                .defaultSuccessUrl("/seller/home", true)

                .and()
                .logout();

    }

    private static CsrfTokenRepository csrfTokenRepository() {
        var csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        csrfTokenRepository.setSessionAttributeName("_csrf");
        return csrfTokenRepository;
    }

}
