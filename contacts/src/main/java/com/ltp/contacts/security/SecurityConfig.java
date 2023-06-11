package com.ltp.contacts.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.ltp.contacts.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.Optional;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/", "/registrationSubmit").anonymous()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/account/home"))
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/account/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/account/home"))
                .and()
                .httpBasic();

        return http.build();
    }


    @Bean
    public UserDetailsService users() {
        return username -> {
            Optional<com.ltp.contacts.pojo.User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                com.ltp.contacts.pojo.User user = userOptional.get();
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("USER"))
                );
            }
            throw new UsernameNotFoundException("User not found");
        };

    }
}
