package com.mkolongo.price_comparison.web.filters;

import com.mkolongo.price_comparison.domain.JwtPropertiesConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtPropertiesConfig jwtPropertiesConfig;

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(jwtPropertiesConfig.authorizationHeader());

        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(jwtPropertiesConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jws = authorizationHeader.replace(jwtPropertiesConfig.getTokenPrefix(), "");

        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtPropertiesConfig.getSecretKey().getBytes()))
                    .parseClaimsJws(jws)
                    .getBody();

            final String username = claims.getSubject();
            final var authorities = (List<Map<String, Object>>) claims.get("authorities");

            Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(((String) authority.get("authority"))))
                    .collect(Collectors.toSet());

            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (JwtException e) {
            throw new IllegalStateException(MessageFormat.format("Jws {0} cannot be trusted!", jws));
        }

        filterChain.doFilter(request, response);
    }
}
