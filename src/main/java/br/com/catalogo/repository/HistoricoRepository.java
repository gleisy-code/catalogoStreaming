/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.catalogo.repository;

import br.com.catalogo.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    @Query("SELECT h FROM Historico h WHERE h.usuario.usuario_id = :usuarioId AND h.assistidoCompleto = false")
    List<Historico> buscarPorUsuarioEIncompleto(@Param("usuarioId") Long usuarioId);
}