/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.service.MinhaListaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author gleisy
 */
@RestController
@RequestMapping("/minhalista")
public class MinhaListaController {

    @Autowired
    private MinhaListaService minhaListaService;

    // Adicionar aos Favoritos (Requer Plano)
    @PostMapping("/adicionar/{usuarioId}")
    public ResponseEntity<?> adicionarAosFavoritos(@PathVariable Long usuarioId, @RequestBody MinhaLista itemFavorito) {
        try {
            MinhaLista novoFavorito = minhaListaService.adicionarAosFavoritos(usuarioId, itemFavorito);
            return ResponseEntity.status(201).body(novoFavorito); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage()); // 403 Forbidden (Plano Inativo)
        }
    }

    // Remover dos Favoritos (Requer Plano)
    @DeleteMapping("/remover/{idFavorito}/usuario/{usuarioId}")
    public ResponseEntity<?> removerDosFavoritos(@PathVariable Long usuarioId, @PathVariable Long idFavorito) {
        try {
            minhaListaService.removerDosFavoritos(usuarioId, idFavorito);
            return ResponseEntity.ok("Item removido dos favoritos com sucesso!");
        } catch (RuntimeException e) {
            // Pode falhar por plano inativo (403) ou ID não existente (404)
            if (e.getMessage().contains("negado")) {
                return ResponseEntity.status(403).body(e.getMessage());
            }
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Listar Favoritos do Usuário (Requer Plano)
    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<?> listarFavoritosDoUsuario(@PathVariable Long usuarioId) {
        try {
            List<MinhaLista> favoritos = minhaListaService.listarFavoritosDoUsuario(usuarioId);
            return ResponseEntity.ok(favoritos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}