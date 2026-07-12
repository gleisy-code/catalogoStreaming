/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinhaListaDTO {

	//anotação que some com a variavel, no arquivo json do swegger
	@io.swagger.v3.oas.annotations.media.Schema(accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY)
    private Long id;

    // O id do usuário virá preferencialmente pela URL, mas mantemos o campo se necessário
    private Long usuarioId;

    // O usuário pode favoritar OU um filme OU uma série
    private Long filmeId;
    private Long serieId;
}