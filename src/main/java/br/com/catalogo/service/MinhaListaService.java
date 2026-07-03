/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.model.Usuario;
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

    public MinhaLista adicionarAosFavoritos(Long usuarioId, MinhaLista itemFavorito) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        // Garante que o ID do usuário enviado na URL seja injetado no objeto antes de salvar
        if (itemFavorito.getUsuario() == null) {
            Usuario user = new Usuario();
            user.setUsuario_id(usuarioId);
            itemFavorito.setUsuario(user);
        } else {
            itemFavorito.getUsuario().setUsuario_id(usuarioId);
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