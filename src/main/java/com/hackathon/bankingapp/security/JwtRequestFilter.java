package com.hackathon.bankingapp.security;

import com.hackathon.bankingapp.services.BlacklistService;
import com.hackathon.bankingapp.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final BlacklistService blacklistService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService, BlacklistService blacklistService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.blacklistService = blacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(("Bearer "))){
            jwt = authorizationHeader.substring(7);
            boolean jwtIsValid = blacklistService.tokenIsValid(jwt);
            if (jwtIsValid) {
                try {
                    username = jwtUtil.extractUsername(jwt);
                    log.info("Received an authenticated request from: {}", username);
                } catch (JwtException e) {
                    log.warn("Invalid JWT: {}", e.getMessage());
                }
            }else {
                log.warn("Invalid JWT: jwt present in blacklist.");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        if (username == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Access Denied");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
