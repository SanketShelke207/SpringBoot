package com.yash.MovieBooking.conffig;

import com.yash.MovieBooking.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security settings.  Defines authentication and
 * authorization rules for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain.  Configures request authorization,
     * authentication methods, and other security features.
     *
     * @param http HttpSecurity object for configuring security.
     * @return SecurityFilterChain object representing the configured filter chain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register").permitAll()

                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    /**
     * Defines the password encoder to be used for encrypting user passwords.
     *
     * @return PasswordEncoder object using BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures a DaoAuthenticationProvider, which uses a UserDetailsService
     * to retrieve user details and a PasswordEncoder to verify passwords.
     *
     * @param userDetailsService The CustomUserDetailsService implementation.
     * @param passwordEncoder    The PasswordEncoder to use.
     * @return DaoAuthenticationProvider object.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Retrieves the AuthenticationManager from the AuthenticationConfiguration.
     *
     * @param config AuthenticationConfiguration object.
     * @return AuthenticationManager object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}