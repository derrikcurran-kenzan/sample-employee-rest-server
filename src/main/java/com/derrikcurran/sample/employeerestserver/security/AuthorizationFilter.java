package com.derrikcurran.sample.employeerestserver.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private SecurityConfig config;
    private UserDetailsServiceImpl userDetailsService;

    AuthorizationFilter(
        AuthenticationManager authManager,
        SecurityConfig config,
        UserDetailsServiceImpl userDetailsService
    ) {
        super(authManager);
        this.config = config;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException {
        String header = req.getHeader(config.getAuthHeaderKey());

        if (header != null && header.startsWith(config.getTokenPrefix())) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(req));
        }

        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        UsernamePasswordAuthenticationToken authentication = null;

        String token = req.getHeader(config.getAuthHeaderKey());

        if (token != null) {
            String subject = Jwts.parser()
                .setSigningKey(config.getTokenSecret().getBytes())
                .parseClaimsJws(token.replace(config.getTokenPrefix(), ""))
                .getBody()
                .getSubject();

            if (subject != null) {
                Long userId = Long.parseLong(subject);
                UserDetailsImpl user = userDetailsService.loadUserById(userId);
                if (user != null) {
                    authentication = new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            null,
                            new ArrayList<>()
                    );
                }
            }
        }
        return authentication;
    }
}
