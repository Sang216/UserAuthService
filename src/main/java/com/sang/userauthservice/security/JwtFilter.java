package com.sang.userauthservice.security;

import com.sang.userauthservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && !header.startsWith("Bearer ")) {
            try {
                Claims claims = jwtService.parseToken(header.substring("Bearer ".length()));

                List<GrantedAuthority> grantedAuthorities = List.of(
                        new SimpleGrantedAuthority("ROLE_" + claims.get("role").toString())    // Spring Convention
                );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(), null, grantedAuthorities);

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            } catch (JwtException e) {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        }
    }
}
