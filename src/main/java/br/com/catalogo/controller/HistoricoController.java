/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.DTO.HistoricoDTO;
import br.com.catalogo.model.Historico;
import br.com.catalogo.service.HistoricoService;
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
@RequestMapping("/historico")
@Tag(
        name = "Histórico de Visualização",
        description = "Endpoints responsáveis por registrar e listar os conteúdos que o usuário já assistiu no sistema."
)
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping("/progresso-filme")
    public ResponseEntity<?> registrarProgressoFilme(@RequestBody @Valid HistoricoDTO historicoDTO) {
        try {
            Historico novoHistorico = historicoService.registrarProgressoFilme(historicoDTO);
            return ResponseEntity.status(201).body(novoHistorico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/progresso-serie")
    public ResponseEntity<?> registrarProgressoSerie(@RequestBody @Valid HistoricoDTO historicoDTO) {
        try {
            Historico novoHistorico = historicoService.registrarProgressoSerie(historicoDTO);
            return ResponseEntity.status(201).body(novoHistorico);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/continuar/{usuarioId}")
    public ResponseEntity<?> listarContinuarAssistindo(@PathVariable Long usuarioId) {
        try {
            List<Historico> itensIncompletos = historicoService.listarContinuarAssistindo(usuarioId);
            return ResponseEntity.ok(itensIncompletos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}