/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.Episodio;
import br.com.catalogo.model.Serie;
import br.com.catalogo.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Listar series, abertura basica, buscar por nome, procurar por genero, procurar por imdb.
 * Do admin: adicionar novas series, adicionar ep, remover serie do catalogo.
 * @author gleisy
 */
@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    // FUNCIONALIDADES DO USUÁRIO (REQUER PLANO ATIVO)

    /**
     * Abertura básica: Listar todas as séries disponíveis.
     */
    public List<Serie> listarTodas(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        return serieRepository.findAll();
    }

    /**
     * Buscar série pelo nome (título).
     */
    public List<Serie> buscarPorNome(Long usuarioId, String nome) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        List<Serie> todasSeries = serieRepository.findAll();
        List<Serie> resultadoBusca = new ArrayList<>();

        for (Serie s : todasSeries) {
            if (s.getTitulo() != null && s.getTitulo().toLowerCase().contains(nome.toLowerCase())) {
                resultadoBusca.add(s);
            }
        }
        return resultadoBusca;
    }

    /**
     * Procurar séries por gênero.
     */
    public List<Serie> procurarPorGenero(Long usuarioId, String genero) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        List<Serie> todasSeries = serieRepository.findAll();
        List<Serie> seriesFiltradas = new ArrayList<>();

        for (Serie s : todasSeries) {
            if (s.getGenero() != null && s.getGenero().equalsIgnoreCase(genero)) {
                seriesFiltradas.add(s);
            }
        }
        return seriesFiltradas;
    }

    /**
     * Procurar séries por uma nota mínima do IMDb.
     */
    public List<Serie> procurarPorImdb(Long usuarioId, Double notaMinima) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        List<Serie> todasSeries = serieRepository.findAll();
        List<Serie> seriesFiltradas = new ArrayList<>();

        for (Serie s : todasSeries) {
            if (s.getImbd() != null && s.getImbd() >= notaMinima) {
                seriesFiltradas.add(s);
            }
        }
        return seriesFiltradas;
    }

    // =========================================================================
    // FUNCIONALIDADES DO ADMINISTRADOR (REQUER SER ADMIN OFICIAL)
    // =========================================================================

    /**
     * Adicionar nova série ao catálogo.
     */
    public Serie adicionarSerie(Long adminId, Serie serie) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem adicionar séries.");
        }
        return serieRepository.save(serie);
    }

    /**
     * Adicionar um novo episódio a uma série existente.
     */
    public Serie adicionarEpisodio(Long adminId, Long serieId, Episodio novoEpisodio) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem gerenciar episódios.");
        }

        // Busca a série no banco usando o método padrão do JPA
        java.util.Optional<Serie> serieOpt = serieRepository.findById(serieId);
        
        if (serieOpt.isPresent()) {
            Serie serie = serieOpt.get();
            // Adiciona o novo episódio na lista interna da série
            serie.getEpsodios().add(novoEpisodio);
            // Salva a série atualizada (o JPA cascade cuida do episódio se estiver configurado)
            return serieRepository.save(serie);
        } else {
            throw new RuntimeException("Série não encontrada com o ID: " + serieId);
        }
    }

    /**
     * Remover série do catálogo.
     */
    public void removerSerie(Long adminId, Long serieId) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem remover séries.");
        }
        
        if (serieRepository.existsById(serieId)) {
            serieRepository.deleteById(serieId);
        }
    }
}