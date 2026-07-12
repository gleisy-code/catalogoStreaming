/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoDTO {

	@io.swagger.v3.oas.annotations.media.Schema(accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "O ponto onde parou de assistir deve ser informado")
    @PositiveOrZero(message = "O tempo assistido não pode ser negativo")
    private Integer ondeParou;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    private Long filmeId;
    private Long serieId;
    private Long episodioId;
}