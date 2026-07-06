/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.DTO.UsuarioDTO;
import br.com.catalogo.model.Usuario;
import br.com.catalogo.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public void deleteUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível remover. Usuário não encontrado para o id: " + id);
        }
    }
    
    // CONVERSÃO MANUAL NO CADASTRO (POST)
    public Usuario cadastrarUsuario(UsuarioDTO usuariodto) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(usuariodto.getNomeUsuario());
        usuario.setEmail(usuariodto.getEmail());
        usuario.setSenha(usuariodto.getSenha());
        usuario.setPlanoAtivo(usuariodto.isPlanoAtivo());
        usuario.setEhAdmin(usuariodto.isEhAdmin());

        if (usuario.isPlanoAtivo()) {
            usuario.setDataAtivacaoPlano(LocalDate.now());
        }
        return usuarioRepository.save(usuario);
    }

    // CONVERSÃO E ATUALIZAÇÃO MANUAL NA EDIÇÃO (PUT)
    public void editarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Falha na atualização! Usuário não encontrado para o id: " + id));

        // Transfere os dados do DTO para a entidade encontrada
        usuarioExistente.setNomeUsuario(usuarioDTO.getNomeUsuario());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSenha(usuarioDTO.getSenha());
        usuarioExistente.setPlanoAtivo(usuarioDTO.isPlanoAtivo());
        usuarioExistente.setEhAdmin(usuarioDTO.isEhAdmin());

        usuarioRepository.save(usuarioExistente);
    }
    
    public boolean verificarAcessoUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            if (usuario.isPlanoAtivo() && usuario.getDataAtivacaoPlano() != null) {
                LocalDate dataVencimento = usuario.getDataAtivacaoPlano().plusMonths(1);
                
                if (LocalDate.now().isAfter(dataVencimento)) {
                    usuario.setPlanoAtivo(false);
                    usuarioRepository.save(usuario); 
                }
            }
            
            return usuario.isPlanoAtivo();
        }
        
        return false; 
    }
    
    public boolean ehAdminOficial(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.isEhAdmin(); 
        }
        
        return false;
    }
}