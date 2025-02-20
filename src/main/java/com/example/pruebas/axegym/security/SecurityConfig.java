package com.example.pruebas.axegym.security;

import com.example.pruebas.axegym.User.Permission;
import com.example.pruebas.axegym.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig->csrfConfig.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sessionMangConfig-> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig->{
                    authConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET,"/auth/public-access").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    authConfig.requestMatchers(HttpMethod.GET,"/clients/**").hasAuthority(Permission.CREATE_ONE_CLIENT.name());     
                    authConfig.requestMatchers(HttpMethod.POST,"/clients/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,"/trainers/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET,"/trainers/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.DELETE,"/clients/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,"/attendance/**").hasAuthority(Permission.CREATE_ONE_CLIENT.name());
                    authConfig.requestMatchers(HttpMethod.GET,"/attendance/**").hasAuthority(Permission.CREATE_ONE_CLIENT.name());
                    authConfig.requestMatchers(HttpMethod.POST,"/membership/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET,"/membership/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.DELETE,"/membership/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,"/schedule/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET,"/schedule/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET,"/plan/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST,"/plan/**").permitAll();

                    authConfig.anyRequest().denyAll();
                });

        return http.build();
    }

    @Bean public CorsConfigurationSource corsConfigurationSource() { CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); return source; }

}




