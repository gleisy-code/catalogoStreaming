/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Serie;
import br.com.catalogo.repository.SerieRepository;
import java.util.ArrayList;
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
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;
   
    @PostMapping("/cadastrar")
    public void cadastrarSerie(@RequestBody Serie s) {
        serieRepository.save(s);
    }
   
    @GetMapping
    public List<Serie> listarSerie() {
        return serieRepository.findAll();
    }
   
    @DeleteMapping("/remover")
    public void deleteSerie(@RequestParam long id) {
        if (serieRepository.existsById(id)) {
            serieRepository.deleteById(id);
        }
    }
    
    @PutMapping("/editar/{id}")
    public void editarSerie(@PathVariable long id, @RequestBody Serie serieAtualizada) {
        if (serieRepository.existsById(id)) {
            serieAtualizada.setId(id);
            serieRepository.save(serieAtualizada);
        }
    }
}
