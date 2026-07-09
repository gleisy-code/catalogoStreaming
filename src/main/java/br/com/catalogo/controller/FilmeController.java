/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.FilmeDTO;
import br.com.catalogo.model.Filme;
import br.com.catalogo.service.FilmeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author gleisy
 */
@RestController
@RequestMapping("/Filme")
@Tag(
        name = "Filmes",
        description = "Endpoints responsáveis pelo cadastro, listagem, edição e remoção de filmes no catálogo."
)
public class FilmeController {
    
    @Autowired
    private FilmeService filmeService;
    
    @PostMapping("/cadastrar/{adminId}")
    public ResponseEntity<?> adicionarFilme(@PathVariable Long adminId, @RequestBody @Valid FilmeDTO filme) {
        try {
            Filme filmeSalvo = filmeService.adicionarFilme(adminId, filme);
            return ResponseEntity.status(201).body(filmeSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<?> listarTodosFilmes(@PathVariable Long usuarioId) {
        try {
            List<Filme> filmes = filmeService.listarTodos(usuarioId);
            return ResponseEntity.ok(filmes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @PutMapping("/editar/{filmeId}/admin/{adminId}")
    public ResponseEntity<?> editarFilme(@PathVariable Long adminId, @PathVariable Long filmeId, @RequestBody @Valid FilmeDTO filmeDTO) {
        try {
            filmeService.editarFilme(adminId, filmeId, filmeDTO);
            return ResponseEntity.ok("Filme atualizado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/remover/{filmeId}/admin/{adminId}")
    public ResponseEntity<?> removerFilme(@PathVariable Long adminId, @PathVariable Long filmeId) {
        try {
            filmeService.removerFilme(adminId, filmeId);
            return ResponseEntity.ok("Filme removido com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
    
    @GetMapping("/genero")
    public ResponseEntity<?> filtrarPorGenero(@RequestParam Long usuarioId, @RequestParam String genero) {
        try {
            List<Filme> filmes = filmeService.filtrarPorGenero(usuarioId, genero);
            if (!filmes.isEmpty()) {
                return ResponseEntity.ok(filmes);
            } else {
                return ResponseEntity.status(404).body("Nenhum filme encontrado para o gênero: " + genero);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/imdb")
    public ResponseEntity<?> filtrarPorImdb(@RequestParam Long usuarioId, @RequestParam Double notaMinima) {
        try {
            List<Filme> filmes = filmeService.filtrarPorImdb(usuarioId, notaMinima);
            if (!filmes.isEmpty()) {
                return ResponseEntity.ok(filmes);
            } else {
                return ResponseEntity.status(404).body("Nenhum filme encontrado com nota IMDb maior ou igual a: " + notaMinima);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}