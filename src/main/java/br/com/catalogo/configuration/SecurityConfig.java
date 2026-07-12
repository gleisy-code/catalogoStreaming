package br.com.catalogo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Rotas Públicas: Login e TODO o ecossistema do Swagger (Primeiro lugar)
                		// 1. Rotas Públicas: Login, Cadastro de Usuário e TODO o ecossistema do Swagger
                		.requestMatchers(
                		        "/auth/login", 
                		        "/usuario/cadastrar", // <-- LINHA ADICIONADA AQUI
                		        "/swagger-ui/index.html", 
                		        "/v3/api-docs",
                		        "/swagger-ui/**",
                		        "/webjars/**", 
                		        "/v3/api-docs/**",
                		        "/v3/api-docs/swagger-config"
                		).permitAll()

                        // 2. Rotas do seu catálogo que só o ADMIN pode acessar (Trata maiúsculas/minúsculas e plural/singular)
                        .requestMatchers("/filmes/cadastrar", "/Filme/cadastrar/**", "/series/cadastrar", "/Serie/cadastrar/**").hasRole("ADMIN")
                        
                        // 3. Rotas do seu catálogo que qualquer um logado (USER ou ADMIN) pode acessar
                        .requestMatchers("/filmes/**", "/Filme/**", "/series/**", "/Serie/**").authenticated()
                        
                        // 4. Qualquer outra rota não mapeada exige autenticação
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Altere os nomes de exibição das variáveis se preferir, mas mantenha a lógica
        var usuarioComum = User.builder()
                .username("usuario")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

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