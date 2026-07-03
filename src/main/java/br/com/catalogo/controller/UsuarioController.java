/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Usuario;
import br.com.catalogo.repository.UsuarioRepository;
import br.com.catalogo.service.UsuarioService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gleisy
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;
   
    @PostMapping("/cadastrar")
    public Usuario cadastrarUsuario(@RequestBody Usuario u) {
        return usuarioService.cadastrarUsuario(u);
    }
   
    @GetMapping
    public List<Usuario> listarUsuarios() {
        
        return usuarioService.listarUsuarios();
        
    }
   
    @DeleteMapping("/remover")
    public void deleteUsuario(@RequestParam long id) {
        
    }
    
    @PutMapping("/editar/{id}")
    public void editarUsuario(@PathVariable long id, @RequestBody Usuario usuarioAtualizado) {
        
    }
    
    @GetMapping("/verificar-acesso/{id}")
    public ResponseEntity<Boolean> verificarAcessoUsuario(@PathVariable Long id) {
        boolean temAcesso = usuarioService.verificarAcessoUsuario(id);
        return ResponseEntity.ok(temAcesso);
    }
    
    
    @GetMapping("/verificar-admin/{id}")
    public ResponseEntity<Boolean> ehAdminOficial(@PathVariable Long id) {
        boolean isAdmin = usuarioService.ehAdminOficial(id);
        return ResponseEntity.ok(isAdmin);
    }
    

}
