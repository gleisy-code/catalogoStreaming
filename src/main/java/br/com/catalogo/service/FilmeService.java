/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.DTO.FilmeDTO;
import br.com.catalogo.model.Filme;
import br.com.catalogo.repository.FilmeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gleisy
 */
@Service 
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
                filmesFiltrados.add(f);//VERIFIQUE ESSA PARTE
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
    
    // CONVERSÃO MANUAL NO CADASTRO (POST)
    public Filme adicionarFilme(Long adminId, FilmeDTO filmeDTO) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem adicionar filmes.");
        }
        
        Filme filme = new Filme();
        filme.setNomeFilme(filmeDTO.getNomeFilme());
        filme.setLancamento(filmeDTO.getLancamento());
        filme.setImdb(filmeDTO.getImdb());
        filme.setGenero(filmeDTO.getGenero());
        filme.setDuracao(filmeDTO.getDuracao());
        filme.setSinopse(filmeDTO.getSinopse());
        filme.setURL(filmeDTO.getURL());
        
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

    // CONVERSÃO E ATUALIZAÇÃO MANUAL NA EDIÇÃO (PUT)
    public void editarFilme(Long adminId, Long filmeId, FilmeDTO filmeDTO) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem editar filmes.");
        }
        
        Filme filmeExistente = filmeRepository.findById(filmeId)
            .orElseThrow(() -> new RuntimeException("Falha na atualização! Filme não encontrado para o id: " + filmeId));
            
        filmeExistente.setNomeFilme(filmeDTO.getNomeFilme());
        filmeExistente.setLancamento(filmeDTO.getLancamento());
        filmeExistente.setImdb(filmeDTO.getImdb());
        filmeExistente.setGenero(filmeDTO.getGenero());
        filmeExistente.setDuracao(filmeDTO.getDuracao());
        filmeExistente.setSinopse(filmeDTO.getSinopse());
        filmeExistente.setURL(filmeDTO.getURL());
        
        filmeRepository.save(filmeExistente);
    }
}