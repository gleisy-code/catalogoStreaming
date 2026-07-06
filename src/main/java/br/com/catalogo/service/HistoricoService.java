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

        Historico historico = new Historico();
        historico.setOndeParou(dto.getOndeParou());
        
        Usuario usuario = new Usuario();
        usuario.setUsuario_id(dto.getUsuarioId());
        historico.setUsuario(usuario);
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

        // 1. Buscamos a série no banco de dados
        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada para o ID: " + dto.getSerieId()));

        // 2. Varremos a lista de episódios da própria série para achar o episódio correspondente
        List<Episodio> listaDeEps = serie.getEpsodios();
        Episodio episodioAlvo = null;

        for (Episodio ep : listaDeEps) {
            if (ep.getId().equals(dto.getEpisodioId())) {
                episodioAlvo = ep;
                break; 
            }
        }

        // Se o id do episódio enviado não estiver na lista da série, barramos aqui
        if (episodioAlvo == null) {
            throw new RuntimeException("O episódio informado não pertence à série indicada ou não existe.");
        }

        Historico historico = new Historico();
        historico.setOndeParou(dto.getOndeParou());

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(dto.getUsuarioId());
        historico.setUsuario(usuario);
        historico.setSerie(serie);
        historico.setEpisodioAtual(episodioAlvo); // Vincula o objeto encontrado na lista

        // 3. Verifica o progresso usando a duração do episódio encontrado
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
        return historicoRepository.findByUsuario_UsuarioIdAndAssistidoCompletoFalse(usuarioId);
    }
}