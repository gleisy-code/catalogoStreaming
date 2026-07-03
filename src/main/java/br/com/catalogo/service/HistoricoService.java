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

/**
 *lista de continuar assistindo, o usuario começou a ver mas não terminou, registrar ep parado 
 * @author gleisy
 */
public class HistoricoService {
    @Autowired
    private HistoricoRepository historicoRepository;
    
    public Historico registrarProgressoFilme(Historico historico) {
        if (historico.getFilme() != null) {
            if (historico.getOndeParou() >= historico.getFilme().getDuracao()) {
                historico.setAssistidoCompleto(true); // Acabou o filme
            } else {
                historico.setAssistidoCompleto(false); // Ainda está pela metade
            }
        }
            // Salva no banco usando o método padrão da JPA
            return historicoRepository.save(historico);
        
    }
    
    
    public Historico registrarProgressoSerie(Historico historico) {
        if (historico.getSerie() != null && historico.getEpisodioAtual() != null) {

            // 1. Pegamos a lista de episódios que pertence a essa série
            List<Episodio> listaDeEps = historico.getSerie().getEpsodios();

            // 2. Procuramos o episódio atual dentro da lista para garantir que ele existe e pegar a duração
            for (Episodio ep : listaDeEps) {
                // Se encontramos o episódio que o usuário está assistindo
                if (ep.getId().equals(historico.getEpisodioAtual().getId())) {

                    // 3. Comparamos onde o usuário parou com a duração DESTE episódio específico
                    if (historico.getOndeParou() >= ep.getDuracao()) {
                        historico.setAssistidoCompleto(true); // Terminou o episódio!
                    } else {
                        historico.setAssistidoCompleto(false); // Parou no meio do episódio
                    }
                    break; // Achou o episódio, pode parar o laço
                }
            }
        }

        // Salva o histórico atualizado no banco
        return historicoRepository.save(historico);
    }
    
    
    public List<Historico> listarContinuarAssistindo(Long usuarioId) {
        return historicoRepository.findByUsuarioUsuarioIdAndAssistidoCompletoFalse(usuarioId);
    }
}
//findByUsuarioIdAndConcluidoFalse(usuarioId);
