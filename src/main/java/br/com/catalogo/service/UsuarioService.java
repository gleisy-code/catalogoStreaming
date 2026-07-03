/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.service;

import br.com.catalogo.model.Usuario;
import br.com.catalogo.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *controle de tempo de plano pra o usuario, reconhecimento se o usuario é admin oficial
 * @author gleisy
 */
@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    public void deleteUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }
    }
    public Usuario cadastrarUsuario(Usuario usuario){
        if (usuario.isPlanoAtivo()) {
            usuario.setDataAtivacaoPlano(LocalDate.now());
        }
        return usuarioRepository.save(usuario);
    }
    
    public void editarUsuario(Long id, Usuario usuarioAtualizado){
        if(usuarioRepository.existsById(id)){
            usuarioAtualizado.setUsuario_id(id);
            usuarioRepository.save(usuarioAtualizado);
        }
    }
    //metodos novos
    public boolean verificarAcessoUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // Se o plano está ativo, verifica se já venceu (30 dias)
            if (usuario.isPlanoAtivo() && usuario.getDataAtivacaoPlano() != null) {
                LocalDate dataVencimento = usuario.getDataAtivacaoPlano().plusMonths(1);
                
                // Se HOJE já passou da data de vencimento, o plano cai
                if (LocalDate.now().isAfter(dataVencimento)) {
                    usuario.setPlanoAtivo(false);
                    usuarioRepository.save(usuario); // Atualiza no banco via JPA
                }
            }
            
            // Retorna o estado atual do plano (true para liberado, false para bloqueado)
            return usuario.isPlanoAtivo();
        }
        
        return false; // Se o usuário não existir, não tem acesso
    }
    public boolean ehAdminOficial(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.isEhAdmin(); // Retorna true ou false do modelo
        }
        
        return false;
    }
    
}
