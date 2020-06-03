package com.mkolongo.product_shop.web.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkolongo.product_shop.domain.JwtPropertiesConfig;
import com.mkolongo.product_shop.domain.models.binding.UserLoginBindingModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtPropertiesConfig jwtPropertiesConfig;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            final UserLoginBindingModel bindingModel =
                    new ObjectMapper().readValue(request.getInputStream(), UserLoginBindingModel.class);

            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(bindingModel.getUsername(), bindingModel.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        final String jws = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtPropertiesConfig.getTokenExpirationDays())))
                .signWith(Keys.hmacShaKeyFor(jwtPropertiesConfig.getSecretKey().getBytes()))
                .compact();

        response.addHeader(jwtPropertiesConfig.authorizationHeader(), jwtPropertiesConfig.getTokenPrefix() + jws);

        chain.doFilter(request, response);
    }
}
