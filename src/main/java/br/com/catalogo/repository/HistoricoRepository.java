/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.catalogo.repository;

import br.com.catalogo.model.Historico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gleisy
 */
@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    
    // Corrigido para refletir o nome do atributo exato da id do usuário (usuario_id)
    List<Historico> findByUsuario_UsuarioIdAndAssistidoCompletoFalse(Long usuarioId);
}