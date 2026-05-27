/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.controller;

import br.com.catalogo.model.Filme;
import br.com.catalogo.repository.FilmeRepository;
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
@RequestMapping("/Filme")
public class FilmeController {
    private List<Filme> filmes = new ArrayList<Filme>();
    
    //Injeção de dependência
    @Autowired
    private FilmeRepository filmeRepository;
    
    @PostMapping("/cadastrar")
    public void CadastrarFilme(@RequestBody Filme f){
        filmeRepository.save(f);
        return;
    }
    @GetMapping
    public List<Filme> listarFilmes(){
        return filmeRepository.findAll();
    }
     @DeleteMapping("/remover/{id}")//corrigir esse depois que tem algo estranho com ele
    public void deleteFilme(@PathVariable long id){
         if(filmeRepository.existsById(id)){
            filmeRepository.deleteById(id);
        }
    }
     @DeleteMapping("/remover")//e esse tambem nos metodos exist e deletebiyid
    //Remover Alunos
    public void deleteFilmeversao02(@RequestParam long id){
        if(filmeRepository.existsById(id)){
            filmeRepository.deleteById(id);
        }
    }
    
    
    @PutMapping("/editar/{id}")
    //Editar Aluno
    public void editarFilme(@PathVariable long id, @RequestBody Filme filmeAtualizado){
        if(filmeRepository.existsById(id)){
            filmeAtualizado.setId(id);
            filmeRepository.save(filmeAtualizado);
        }
    }
}
