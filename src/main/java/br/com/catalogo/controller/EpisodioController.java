/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Episodio;
import br.com.catalogo.repository.EpisodioRepository;
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
@RequestMapping("/episodio")
public class EpisodioController {

    @Autowired
    private EpisodioRepository episodioRepository;
   
    @PostMapping("/cadastrar")
    public void cadastrarEpisodio(@RequestBody Episodio e) {
        episodioRepository.save(e);
    }
   
    @GetMapping
    public List<Episodio> listarEpisodios() {
        return episodioRepository.findAll();
    }
   
    @DeleteMapping("/remover")
    public void deleteEpisodio(@RequestParam long id) {
        if (episodioRepository.existsById(id)) {
            episodioRepository.deleteById(id);
        }
    }
    
    @PutMapping("/editar/{id}")
    public void editarEpisodio(@PathVariable long id, @RequestBody Episodio episodioAtualizado) {
        if (episodioRepository.existsById(id)) {
            episodioAtualizado.setId(id);
            episodioRepository.save(episodioAtualizado);
        }
    }
}
