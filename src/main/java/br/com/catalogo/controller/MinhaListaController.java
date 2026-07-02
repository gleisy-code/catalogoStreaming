/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.repository.MinhaListaRepository;
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
@RequestMapping("/minhalista")
public class MinhaListaController {

    @Autowired
    private MinhaListaRepository minhaListaRepository;
   
    @PostMapping("/cadastrar")
    public void cadastrarNaLista(@RequestBody MinhaLista ml) {
        minhaListaRepository.save(ml);
    }
   
    @GetMapping
    public List<MinhaLista> listarMinhaLista() {
        return minhaListaRepository.findAll();
    }
   
    @DeleteMapping("/remover")
    public void deleteDaLista(@RequestParam long id) {
        if (minhaListaRepository.existsById(id)) {
            minhaListaRepository.deleteById(id);
        }
    }
    
    @PutMapping("/editar/{id}")
    public void editarLista(@PathVariable long id, @RequestBody MinhaLista listaAtualizada) {
        if (minhaListaRepository.existsById(id)) {
            listaAtualizada.setId(id);
            minhaListaRepository.save(listaAtualizada);
        }
    }
}
