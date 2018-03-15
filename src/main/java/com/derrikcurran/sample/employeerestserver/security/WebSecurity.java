package com.derrikcurran.sample.employeerestserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private SecurityConfig config;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurity(SecurityConfig config, UserDetailsServiceImpl userDetailsService) {
        this.config = config;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(authenticationFilter("/auth/sign-in"))
            .addFilter(authorizationFilter())
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/employees/**").permitAll()
                .antMatchers(HttpMethod.POST, "/employees/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/employees/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    private AuthenticationFilter authenticationFilter(String signInUrl) throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), config);
        authenticationFilter.setFilterProcessesUrl(signInUrl);
        return authenticationFilter;
    }

    private AuthorizationFilter authorizationFilter() throws Exception {
        return new AuthorizationFilter(authenticationManager(), config, userDetailsService);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Arrays.asList(
            "*"
        ));

        corsConfig.setAllowedMethods(Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "DELETE"
        ));

        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
