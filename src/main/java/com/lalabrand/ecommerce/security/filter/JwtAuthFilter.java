package com.lalabrand.ecommerce.security.filter;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import com.lalabrand.ecommerce.security.jwt_token.JwtPayload;
import com.lalabrand.ecommerce.security.jwt_token.JwtService;
import com.lalabrand.ecommerce.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    @Value("${auth.header}")
    private String authHeader;
    @Value("${auth.header.start}")
    private String authHeaderStart;

    public JwtAuthFilter(UserDetailsServiceImpl userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        JwtPayload jwtPayload = null;
        String header = request.getHeader(authHeader);
        if (header != null && header.startsWith(authHeaderStart)) {
            jwtPayload = jwtService.parseToken(header.substring(7));
        }

        if (jwtPayload != null && jwtPayload.getEmail() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(jwtPayload.getEmail());

            if (jwtService.validateToken(jwtPayload, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
