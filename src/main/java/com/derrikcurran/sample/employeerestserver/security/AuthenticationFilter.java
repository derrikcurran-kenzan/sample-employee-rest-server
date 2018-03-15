package com.derrikcurran.sample.employeerestserver.security;

import com.derrikcurran.sample.employeerestserver.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private SecurityConfig config;
    private AuthenticationManager authManager;

    AuthenticationFilter(AuthenticationManager authManager, SecurityConfig config) {
        this.authManager = authManager;
        this.config = config;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest req,
        HttpServletResponse res
    ) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
                )
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain,
        Authentication auth
    ) throws IOException, ServletException {
        String token = Jwts.builder()
            .setSubject(String.valueOf(((UserDetailsImpl) auth.getPrincipal()).getId()))
            .setExpiration(Date.from(Instant.now().plus(config.getTokenExpiration())))
            .signWith(SignatureAlgorithm.HS512, config.getTokenSecret().getBytes())
            .compact();

        res.addHeader(config.getAuthHeaderKey(), config.getTokenPrefix() + token);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("token", token);
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        new ObjectMapper().writeValue(res.getWriter(), responseData);
    }
}
