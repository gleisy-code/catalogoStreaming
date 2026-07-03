/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.Episodio;
import br.com.catalogo.model.Historico;
import br.com.catalogo.repository.HistoricoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gleisy
 */
@Service // Inclusão da anotação indispensável para o Spring reconhecer a injeção do Bean
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public Historico registrarProgressoFilme(Historico historico) {
        if (historico.getUsuario() != null && !usuarioService.verificarAcessoUsuario(historico.getUsuario().getUsuario_id())) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        if (historico.getFilme() != null) {
            if (historico.getOndeParou() >= historico.getFilme().getDuracao()) {
                historico.setAssistidoCompleto(true); 
            } else {
                historico.setAssistidoCompleto(false); 
            }
        }
        return historicoRepository.save(historico);
    }
    
    public Historico registrarProgressoSerie(Historico historico) {
        if (historico.getUsuario() != null && !usuarioService.verificarAcessoUsuario(historico.getUsuario().getUsuario_id())) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        if (historico.getSerie() != null && historico.getEpisodioAtual() != null) {
            List<Episodio> listaDeEps = historico.getSerie().getEpsodios();

            for (Episodio ep : listaDeEps) {
                if (ep.getId().equals(historico.getEpisodioAtual().getId())) {
                    if (historico.getOndeParou() >= ep.getDuracao()) {
                        historico.setAssistidoCompleto(true); 
                    } else {
                        historico.setAssistidoCompleto(false); 
                    }
                    break; 
                }
            }
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