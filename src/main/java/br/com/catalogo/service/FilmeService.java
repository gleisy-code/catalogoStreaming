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

/**
 *funcionalidades para usuarios com plano ativo, listar todos os filmes, filtragem de generos, filtro de busca por imdb
 * funcionalidades para admin, adicionar e remover novos filmes, editar informações
 * @author gleisy
 */
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    
    /**
     * Listar todos os filmes se o usuário tiver o plano ativo.
     */
    public List<Filme> listarTodos(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        return filmeRepository.findAll();
    }
    
    /**
     * Filtrar filmes por gênero (na memória usando Java puro).
     */
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
    
    /**
     * Filtrar filmes por uma nota mínima do IMDb.
     */
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
    
    
    /**
     * Adicionar novo filme ao catálogo.
     */
    public Filme adicionarFilme(Long adminId, Filme filme) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem adicionar filmes.");
        }
        return filmeRepository.save(filme);
    }

    /**
     * Remover um filme do catálogo.
     */
    public void removerFilme(Long adminId, Long filmeId) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem remover filmes.");
        }
        if (filmeRepository.existsById(filmeId)) {
            filmeRepository.deleteById(filmeId);
        }
    }

    /**
     * Editar informações de um filme existente.
     */
    public void editarFilme(Long adminId, Long filmeId, Filme filmeAtualizado) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem editar filmes.");
        }
        if (filmeRepository.existsById(filmeId)) {
            filmeAtualizado.setId(filmeId); // Ajuste para o método set do ID da sua classe Filme
            filmeRepository.save(filmeAtualizado);
        }
    }

    
    
}
