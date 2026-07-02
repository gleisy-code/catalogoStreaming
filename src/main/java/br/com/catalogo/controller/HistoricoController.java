/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Historico;
import br.com.catalogo.repository.HistoricoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoRepository historicoRepository;
   
    @PostMapping("/cadastrar")
    public void cadastrarHistorico(@RequestBody Historico h) {
        historicoRepository.save(h);
    }
   
    @GetMapping
    public List<Historico> listarHistorico() {
        return historicoRepository.findAll();
    }
   
    @DeleteMapping("/remover")
    public void deleteHistorico(@RequestParam long id) {
        if (historicoRepository.existsById(id)) {
            historicoRepository.deleteById(id);
        }
    }
    
    @PutMapping("/editar/{id}")
    public void editarHistorico(@PathVariable long id, @RequestBody Historico historicoAtualizado) {
        if (historicoRepository.existsById(id)) {
            historicoAtualizado.setId(id);
            historicoRepository.save(historicoAtualizado);
        }
    }
}
