/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // A mesma chave secreta usada no filtro para garantir que o token seja assinado corretamente
    private final String SECRET_KEY = "alquimiasistemas@cod-chave-secreta-jwt-2026";

    public String login(String username, String password) {
        // Busca o usuário na memória do sistema (usuario ou admin)
        UserDetails user = inMemoryUserDetailsManager.loadUserByUsername(username);

        // Compara a senha digitada com a criptografada usando o passwordEncoder
        if (passwordEncoder.matches(password, user.getPassword())) {
            
            // Constrói o "bilhete" do JWT
            return Jwts.builder()
                    .setSubject(user.getUsername()) // Nome do usuário
                    .claim("roles", user.getAuthorities()) // Permissões (ADMIN/USER)
                    .setIssuedAt(new Date()) // Data de criação
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira em 24h
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8)) // Assina com o algoritmo seguro
                    .compact(); // Junta tudo numa string única de token
        } else {
            throw new RuntimeException("Senha Inválida");
        }
    }
}