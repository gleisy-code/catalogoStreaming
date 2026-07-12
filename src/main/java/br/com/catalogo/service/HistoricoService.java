/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.DTO.HistoricoDTO;
import br.com.catalogo.model.Episodio;
import br.com.catalogo.model.Filme;
import br.com.catalogo.model.Historico;
import br.com.catalogo.model.Serie;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.repository.HistoricoRepository;
import br.com.catalogo.repository.FilmeRepository;
import br.com.catalogo.repository.SerieRepository;
import br.com.catalogo.repository.UsuarioRepository; // Import necessário adicionado
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gleisy
 */
@Service 
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Injeção do repositório adicionada

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SerieRepository serieRepository;
    
    public Historico registrarProgressoFilme(HistoricoDTO dto) {
        if (!usuarioService.verificarAcessoUsuario(dto.getUsuarioId())) { 
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        if (dto.getFilmeId() == null) {
            throw new RuntimeException("O ID do filme deve ser informado para registrar o progresso.");
        }

        Filme filme = filmeRepository.findById(dto.getFilmeId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado para o ID: " + dto.getFilmeId()));

        // CORREÇÃO: Busca o usuário completo direto do banco de dados
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + dto.getUsuarioId()));

        Historico historico = new Historico();
        historico.setOndeParou(dto.getOndeParou()); 
        historico.setUsuario(usuario); // Vincula o usuário completo com nome, email, etc.
        historico.setFilme(filme);

        if (historico.getOndeParou() >= filme.getDuracao()) {
            historico.setAssistidoCompleto(true); 
        } else {
            historico.setAssistidoCompleto(false); 
        }

        return historicoRepository.save(historico);
    }
    
    public Historico registrarProgressoSerie(HistoricoDTO dto) {
        if (!usuarioService.verificarAcessoUsuario(dto.getUsuarioId())) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        if (dto.getSerieId() == null || dto.getEpisodioId() == null) {
            throw new RuntimeException("Os IDs da série e do episódio são obrigatórios.");
        }

        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada para o ID: " + dto.getSerieId()));

        List<Episodio> listaDeEps = serie.getEpsodios();
        Episodio episodioAlvo = null;

        for (Episodio ep : listaDeEps) {
            if (ep.getId().equals(dto.getEpisodioId())) {
                episodioAlvo = ep;
                break; 
            }
        }

        if (episodioAlvo == null) {
            throw new RuntimeException("O episódio informado não pertence à série indicada ou não existe.");
        }

        // CORREÇÃO: Busca o usuário completo direto do banco de dados
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para o ID: " + dto.getUsuarioId()));

        Historico historico = new Historico();
        historico.setOndeParou(dto.getOndeParou());
        historico.setUsuario(usuario); // Vincula o usuário completo com nome, email, etc.
        historico.setSerie(serie);
        historico.setEpisodioAtual(episodioAlvo); 

        if (historico.getOndeParou() >= episodioAlvo.getDuracao()) {
            historico.setAssistidoCompleto(true); 
        } else {
            historico.setAssistidoCompleto(false); 
        }

        return historicoRepository.save(historico);
    }
    
    public List<Historico> listarContinuarAssistindo(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        return historicoRepository.buscarPorUsuarioEIncompleto(usuarioId);
    }
}