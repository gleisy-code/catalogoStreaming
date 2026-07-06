/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Mantido para os testes no Postman funcionarem
                .authorizeHttpRequests(auth -> auth
                        // Bloqueia POST, PUT e DELETE em filmes e séries apenas para ADMIN
                        .requestMatchers(HttpMethod.POST, "/filmes/**", "/series/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/filmes/**", "/series/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/filmes/**", "/series/**").hasRole("ADMIN")
                        
                        // Todas as outras rotas (como o histórico ou listagens) exigem apenas estar logado
                        .anyRequest().authenticated() 
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Usuário comum do seu catálogo (pode ser o cliente/comum)
        var usuarioComum = User.builder()
                .username("usuario")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        // Usuário administrador do catálogo (pode cadastrar filmes/séries)
        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("142536"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(usuarioComum, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}