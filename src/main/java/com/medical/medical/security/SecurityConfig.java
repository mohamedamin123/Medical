package com.medical.medical.security;

import com.medical.medical.services.impl.AdminServiceImpl;
import com.medical.medical.services.impl.MedecinServiceImpl;
import com.medical.medical.services.impl.SecretaireServiceImpl;
import com.medical.medical.services.interf.MedecinService;
import com.medical.medical.services.interf.SecretaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MedecinServiceImpl medecinService;
    private final SecretaireServiceImpl secretaireService;
    private final AdminServiceImpl adminService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // Allow login requests
                        .requestMatchers("/admins/admin/**").hasRole("ADMIN") // Role ADMIN for /admin/** endpoints
                        .requestMatchers("/medecins/medecin/**").hasAnyRole("MEDECIN","ADMIN") // Role MEDECIN for /medecins/medecin/** endpoints
                        .requestMatchers("/secretaires/secretaire/**").hasAnyRole("SECRETAIRE","MEDECIN","ADMIN") // Role SECRETAIRE for /secretaires/secretaire/** endpoints
                        .anyRequest().authenticated() // Require authentication for all o
                )
                .httpBasic(withDefaults()) // Use HTTP Basic Authentication
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .cors(AbstractHttpConfigurer::disable) // Disable CORS if not needed
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("Attempting to load user: " + username);

            UserDetails user = null;

            try {
                user = adminService.loadUserByUsername(username);
                if (user != null) {

                    return user;
                }
            } catch (Exception e) {

            }

            try {
                user = secretaireService.loadUserByUsername(username);
                if (user != null) {
                    return user;
                }
            } catch (Exception e) {
            }

            try {
                user = medecinService.loadUserByUsername(username);
                if (user != null) {

                    return user;
                }
            } catch (Exception e) {
                System.out.println("Error in MedecinService: " + e.getMessage());
            }

            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        };
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }
}