/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.catalogo.repository;


import br.com.catalogo.model.MinhaLista;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author gleisy
 */
@Repository
public interface MinhaListaRepository extends JpaRepository<MinhaLista, Long>{
	@Transactional
    @Modifying
    @Query("DELETE FROM MinhaLista m WHERE m.usuario.usuario_id = :usuarioId")
    void deletarPorUsuarioId(@Param("usuarioId") Long usuarioId);
}
