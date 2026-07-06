/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.MinhaListaDTO;
import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.service.MinhaListaService;
import jakarta.validation.Valid;
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

    // Adicionar aos Favoritos (Requer Plano) - Usando DTO agora
    @PostMapping("/adicionar/{usuarioId}")
    public ResponseEntity<?> adicionarAosFavoritos(@PathVariable Long usuarioId, @RequestBody @Valid MinhaListaDTO itemFavoritoDTO) {
        try {
            MinhaLista novoFavorito = minhaListaService.adicionarAosFavoritos(usuarioId, itemFavoritoDTO);
            return ResponseEntity.status(201).body(novoFavorito);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // Remover dos Favoritos (Requer Plano)
    @DeleteMapping("/remover/{idFavorito}/usuario/{usuarioId}")
    public ResponseEntity<?> removerDosFavoritos(@PathVariable Long usuarioId, @PathVariable Long idFavorito) {
        try {
            minhaListaService.removerDosFavoritos(usuarioId, idFavorito);
            return ResponseEntity.ok("Item removido dos favoritos com sucesso!");
        } catch (RuntimeException e) {
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