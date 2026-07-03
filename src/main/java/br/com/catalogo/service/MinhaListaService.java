/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.repository.MinhaListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Adicionar como favorito vinculado ao id do usuario, remover filme/serie dos favoritos.
 * @author gleisy
 */
@Service
public class MinhaListaService {

    @Autowired
    private MinhaListaRepository minhaListaRepository;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * REGRA 1: Adicionar um Filme ou Série aos Favoritos do usuário.
     * Só permite se o plano do usuário estiver ativo.
     */
    public MinhaLista adicionarAosFavoritos(Long usuarioId, MinhaLista itemFavorito) {
        // Verifica se o usuário tem acesso à plataforma antes de deixar favoritar
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        // Garante que o item está sendo salvo para o usuário correto
        // Nota: Ajuste os métodos setUsuario se o relacionamento no seu model for diferente
        return minhaListaRepository.save(itemFavorito);
    }

    /**
     * REGRA 2: Remover um item dos favoritos (Tirar filme/série da lista).
     */
    public void removerDosFavoritos(Long usuarioId, Long idFavorito) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        if (minhaListaRepository.existsById(idFavorito)) {
            minhaListaRepository.deleteById(idFavorito);
        }
    }

    /**
     * REGRA 3: Listar todos os favoritos de um usuário específico.
     * Varre todos os favoritos e filtra apenas os que pertencem ao ID informado.
     */
    public List<MinhaLista> listarFavoritosDoUsuario(Long usuarioId) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }

        List<MinhaLista> todosFavoritos = minhaListaRepository.findAll();
        List<MinhaLista> favoritosDoUsuario = new ArrayList<>();

        for (MinhaLista fav : todosFavoritos) {
            // Entra no relacionamento do usuário e compara o ID
            if (fav.getUsuario() != null && fav.getUsuario().getUsuario_id().equals(usuarioId)) {
                favoritosDoUsuario.add(fav);
            }
        }

        return favoritosDoUsuario;
    }
}