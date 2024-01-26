package com.emma.firstapp;

import com.emma.firstapp.user.User;
import com.emma.firstapp.user.data.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Authenticated Users for Spring Security
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            Optional<User> optionalUser = userRepo.findByUsername(username);
            return optionalUser.map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).build()).orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
        };
    }

    //Set Rules for Requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests ->
                        requests.requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                                .requestMatchers("/home", "/intro").hasRole("USER")
                                .requestMatchers("/", "/**").permitAll())
                .formLogin(form -> form.loginPage("/login")).csrf(AbstractHttpConfigurer::disable).build();
    }

}