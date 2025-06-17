package br.com.rebeca.ToDoList.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/h2-console/**").permitAll() // Permitir acesso ao H2 Console
                        .anyRequest().authenticated() // Requer autenticação para outras requisições
                )
                .csrf(csrf -> csrf
                        .disable() // Desabilitar CSRF
                );

        return http.build();
    }

    // Adiciona um usuário em memória para teste
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("USER") // Define a role
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}