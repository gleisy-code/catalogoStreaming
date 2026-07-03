/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Usuario;
import br.com.catalogo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired // Corrigido: Faltava essa anotação essencial aqui
    private UsuarioService usuarioService;
   
    // Listar todos os Usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Cadastrar Usuário
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(201).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // Buscar Usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado para o id: " + id);
        }
    }

    // Remover Usuário
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("Usuário removido com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Editar Usuário
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        try {
            usuarioService.editarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Verificar se o plano está ativo (Verdadeiro ou Falso)
    @GetMapping("/verificar-acesso/{id}")
    public ResponseEntity<?> verificarAcessoUsuario(@PathVariable Long id) {
        boolean temAcesso = usuarioService.verificarAcessoUsuario(id);
        return ResponseEntity.ok(temAcesso);
    }

    // Verificar se o usuário é Administrador (Verdadeiro ou Falso)
    @GetMapping("/verificar-admin/{id}")
    public ResponseEntity<?> ehAdminOficial(@PathVariable Long id) {
        boolean isAdmin = usuarioService.ehAdminOficial(id);
        return ResponseEntity.ok(isAdmin);
    }
}