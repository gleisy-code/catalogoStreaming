/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import jakarta.validation.constraints.NotBlank;
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
public class SerieDTO {

    private Long id;

    @NotBlank(message = "O título da série é obrigatório")
    private String titulo;

    @NotBlank(message = "O gênero é obrigatório")
    private String genero;

    @NotNull(message = "A nota IMDb é obrigatória")
    @PositiveOrZero(message = "A nota IMDb deve ser maior ou igual a zero")
    private Double imbd;

    @NotBlank(message = "A sinopse é obrigatória")
    private String sinopse;

    @NotNull(message = "A quantidade de temporadas é obrigatória")
    @PositiveOrZero(message = "A quantidade de temporadas não pode ser negativa")
    private Integer quantTemporadas;
}