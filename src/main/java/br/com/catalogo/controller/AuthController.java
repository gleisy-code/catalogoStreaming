/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.LoginRequestDTO;
import br.com.catalogo.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint responsável por realizar o login e gerar o Token JWT.")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            String token = authService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
            return Map.of("token", token);
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("token", "tente novamente");
        }
    }
}