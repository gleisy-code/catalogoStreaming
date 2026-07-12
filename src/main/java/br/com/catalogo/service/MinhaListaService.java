package br.com.catalogo.service;

import br.com.catalogo.DTO.MinhaListaDTO;
import br.com.catalogo.model.MinhaLista;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.model.Filme;
import br.com.catalogo.model.Serie;
import br.com.catalogo.repository.MinhaListaRepository;
import br.com.catalogo.repository.FilmeRepository; // Adicionado
import br.com.catalogo.repository.SerieRepository; // Adicionado
import br.com.catalogo.repository.UsuarioRepository; // Adicionado
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

    @Autowired
    private FilmeRepository filmeRepository; // Injetado para buscar dados completos

    @Autowired
    private SerieRepository serieRepository; // Injetado para buscar dados completos

    @Autowired
    private UsuarioRepository usuarioRepository; // Injetado para buscar dados completos

    // CONVERSÃO MANUAL DO DTO PARA ENTIDADE NO CADASTRO
    public MinhaLista adicionarAosFavoritos(Long usuarioId, MinhaListaDTO dto) {
        if (!usuarioService.verificarAcessoUsuario(usuarioId)) {
            throw new RuntimeException("Acesso negado: Seu plano está inativo.");
        }
        
        if (dto.getFilmeId() == null && dto.getSerieId() == null) {
            throw new RuntimeException("Falha ao favoritar: Informe pelo menos um Filme ou uma Série.");
        }
        
        MinhaLista itemFavorito = new MinhaLista();
        
        // Busca o Usuário completo do banco para preencher a resposta
        Usuario user = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        itemFavorito.setUsuario(user);
        
        // Busca o Filme completo do banco se enviado no DTO
        if (dto.getFilmeId() != null) {
            Filme filme = filmeRepository.findById(dto.getFilmeId())
                    .orElseThrow(() -> new RuntimeException("Filme não encontrado para o ID: " + dto.getFilmeId()));
            itemFavorito.setFilme(filme);
        }
        
        // Busca a Série completa do banco se enviada no DTO
        if (dto.getSerieId() != null) {
            Serie serie = serieRepository.findById(dto.getSerieId())
                    .orElseThrow(() -> new RuntimeException("Série não encontrada para o ID: " + dto.getSerieId()));
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