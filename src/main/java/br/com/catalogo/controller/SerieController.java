/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.SerieDTO;
import br.com.catalogo.model.Episodio;
import br.com.catalogo.model.Serie;
import br.com.catalogo.service.SerieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    // ==========================================
    // ROTAS DE USUÁRIO (VALIDAM PLANO ATIVO)
    // ==========================================

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<?> listarTodas(@PathVariable Long usuarioId) {
        try {
            List<Serie> series = serieService.listarTodas(usuarioId);
            return ResponseEntity.ok(series);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam Long usuarioId, @RequestParam String nome) {
        try {
            List<Serie> series = serieService.buscarPorNome(usuarioId, nome);
            if (!series.isEmpty()) {
                return ResponseEntity.ok(series);
            } else {
                return ResponseEntity.status(404).body("Série não encontrada para a busca: " + nome);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/genero")
    public ResponseEntity<?> procurarPorGenero(@RequestParam Long usuarioId, @RequestParam String genero) {
        try {
            List<Serie> series = serieService.procurarPorGenero(usuarioId, genero);
            if (!series.isEmpty()) {
                return ResponseEntity.ok(series);
            } else {
                return ResponseEntity.status(404).body("Nenhuma série encontrada para o gênero: " + genero);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @GetMapping("/imdb")
    public ResponseEntity<?> procurarPorImdb(@RequestParam Long usuarioId, @RequestParam Double notaMinima) {
        try {
            List<Serie> series = serieService.procurarPorImdb(usuarioId, notaMinima);
            if (!series.isEmpty()) {
                return ResponseEntity.ok(series);
            } else {
                return ResponseEntity.status(404).body("Nenhuma série encontrada com nota IMDb igual ou maior que: " + notaMinima);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // ==========================================
    // ROTAS DE ADMINISTRADOR
    // ==========================================

    @PostMapping("/cadastrar/{adminId}")
    public ResponseEntity<?> adicionarSerie(@PathVariable Long adminId, @RequestBody @Valid SerieDTO serieDTO) {
        try {
            Serie novaSerie = serieService.adicionarSerie(adminId, serieDTO);
            return ResponseEntity.status(201).body(novaSerie);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/adicionar-episodio/{serieId}/admin/{adminId}")
    public ResponseEntity<?> adicionarEpisodio(@PathVariable Long adminId, @PathVariable Long serieId, @RequestBody Episodio novoEpisodio) {
        try {
            // Nota: Se 'Episodio' possuir um DTO próprio futuramente, altere a assinatura aqui também.
            Serie serieAtualizada = serieService.adicionarEpisodio(adminId, serieId, novoEpisodio);
            return ResponseEntity.status(201).body(serieAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @DeleteMapping("/remover/{serieId}/admin/{adminId}")
    public ResponseEntity<?> removerSerie(@PathVariable Long adminId, @PathVariable Long serieId) {
        try {
            serieService.removerSerie(adminId, serieId);
            return ResponseEntity.ok("Série removida com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}