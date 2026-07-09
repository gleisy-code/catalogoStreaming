/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.UsuarioDTO;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@Tag(
        name = "Usuários",
        description = "Endpoints para gerenciamento de perfis de usuários, cadastro de contas e permissões de acesso."
)
public class UsuarioController {

    @Autowired 
    private UsuarioService usuarioService;
   
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuariodto) {
        try {
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuariodto);
            return ResponseEntity.status(201).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado para o id: " + id);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuário removido com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            usuarioService.editarUsuario(id, usuarioDTO);
            return ResponseEntity.ok("Usuário updated com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/verificar-acesso/{id}")
    public ResponseEntity<?> verificarAcessoUsuario(@PathVariable Long id) {
        boolean temAcesso = usuarioService.verificarAcessoUsuario(id);
        return ResponseEntity.ok(temAcesso);
    }

    @GetMapping("/verificar-admin/{id}")
    public ResponseEntity<?> ehAdminOficial(@PathVariable Long id) {
        boolean isAdmin = usuarioService.ehAdminOficial(id);
        return ResponseEntity.ok(isAdmin);
    }
}