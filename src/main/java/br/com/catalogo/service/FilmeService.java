/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.Filme;
import br.com.catalogo.repository.FilmeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gleisy
 */
@Service // Corrigido: Faltava adicionar essa anotação fundamental aqui
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    public List<Filme> listarTodos(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        return filmeRepository.findAll();
    }
    
    public List<Filme> filtrarPorGenero(Long usuarioId, String genero) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        List<Filme> todosFilmes = filmeRepository.findAll();
        List<Filme> filmesFiltrados = new ArrayList<>();
        
        for (Filme f : todosFilmes) {
            if (f.getGenero() != null && f.getGenero().equalsIgnoreCase(genero)) {
                filmesFiltrados.add(f);
            }
        }
        return filmesFiltrados;
    }
    
    public List<Filme> filtrarPorImdb(Long usuarioId, Double notaMinima) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        List<Filme> todosFilmes = filmeRepository.findAll();
        List<Filme> filmesFiltrados = new ArrayList<>();
        
        for (Filme f : todosFilmes) {
            if (f.getImdb() >= notaMinima) {
                filmesFiltrados.add(f);
            }
        }
        return filmesFiltrados;
    }
    
    public Filme adicionarFilme(Long adminId, Filme filme) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem adicionar filmes.");
        }
        return filmeRepository.save(filme);
    }

    public void removerFilme(Long adminId, Long filmeId) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem remover filmes.");
        }
        if (filmeRepository.existsById(filmeId)) {
            filmeRepository.deleteById(filmeId);
        } else {
            throw new RuntimeException("Filme não encontrado para o id: " + filmeId);
        }
    }

    public void editarFilme(Long adminId, Long filmeId, Filme filmeAtualizado) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem editar filmes.");
        }
        if (filmeRepository.existsById(filmeId)) {
            filmeAtualizado.setId(filmeId);
            filmeRepository.save(filmeAtualizado);
        } else {
            throw new RuntimeException("Falha na atualização! Filme não encontrado para o id: " + filmeId);
        }
    }
}