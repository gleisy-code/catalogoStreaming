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
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Serie> listarTodas(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        return serieRepository.findAll();
    }

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

    public Serie adicionarSerie(Long adminId, Serie serie) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem adicionar séries.");
        }
        return serieRepository.save(serie);
    }

    public Serie adicionarEpisodio(Long adminId, Long serieId, Episodio novoEpisodio) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem gerenciar episódios.");
        }

        Optional<Serie> serieOpt = serieRepository.findById(serieId);
        
        if (serieOpt.isPresent()) {
            Serie serie = serieOpt.get();
            // Vincula o lado inverso da relação se necessário
            novoEpisodio.setSerie(serie); 
            serie.getEpsodios().add(novoEpisodio);
            return serieRepository.save(serie);
        } else {
            throw new RuntimeException("Não foi possível adicionar o episódio. Série não encontrada com o ID: " + serieId);
        }
    }

    public void removerSerie(Long adminId, Long serieId) {
        if (!usuarioService.ehAdminOficial(adminId)) {
            throw new RuntimeException("Acesso negado: Apenas administradores oficiais podem remover séries.");
        }
        
        if (serieRepository.existsById(serieId)) {
            serieRepository.deleteById(serieId);
        } else {
            throw new RuntimeException("Não foi possível remover. Série não encontrada com o ID: " + serieId);
        }
    }
}