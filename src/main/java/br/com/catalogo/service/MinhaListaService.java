/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.DTO.MinhaListaDTO;
import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.model.Filme;
import br.com.catalogo.model.Serie;
import br.com.catalogo.repository.MinhaListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gleisy
 */
@Service
public class MinhaListaService {

    @Autowired
    private MinhaListaRepository minhaListaRepository;

    @Autowired
    private UsuarioService usuarioService;

    // CONVERSÃO MANUAL DO DTO PARA ENTIDADE NO CADASTRO
    public MinhaLista adicionarAosFavoritos(Long usuarioId, MinhaListaDTO dto) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        if (dto.getFilmeId() == null && dto.getSerieId() == null) {
            throw new RuntimeException("Falha ao favoritar: Informe pelo menos um Filme ou uma Série.");
        }
        
        MinhaLista itemFavorito = new MinhaLista();
        
        // Vincula o Usuário da URL
        Usuario user = new Usuario();
        user.setUsuario_id(usuarioId);
        itemFavorito.setUsuario(user);
        
        // Vincula o Filme se enviado no DTO
        if (dto.getFilmeId() != null) {
            Filme filme = new Filme();
            filme.setId(dto.getFilmeId());
            itemFavorito.setFilme(filme);
        }
        
        // Vincula a Série se enviada no DTO
        if (dto.getSerieId() != null) {
            Serie serie = new Serie();
            // Assumindo que a entidade Serie usa id ou similar, ajuste se o nome do campo for diferente
            serie.setId(dto.getSerieId()); 
            itemFavorito.setSerie(serie);
        }
        
        return minhaListaRepository.save(itemFavorito);
    }

    public void removerDosFavoritos(Long usuarioId, Long idFavorito) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        if (minhaListaRepository.existsById(idFavorito)) {
            minhaListaRepository.deleteById(idFavorito);
        } else {
            throw new RuntimeException("Não foi possível remover. Item favorito não encontrado para o ID: " + idFavorito);
        }
    }

    public List<MinhaLista> listarFavoritosDoUsuario(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        List<MinhaLista> todosFavoritos = minhaListaRepository.findAll();
        List<MinhaLista> favoritosDoUsuario = new ArrayList<>();

        for (MinhaLista fav : todosFavoritos) {
            if (fav.getUsuario() != null && fav.getUsuario().getUsuario_id().equals(usuarioId)) {
                favoritosDoUsuario.add(fav);
            }
        }

        return favoritosDoUsuario;
    }
}