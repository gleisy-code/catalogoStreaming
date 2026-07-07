/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilmeDTO {

    private Long id; 

    @NotBlank(message = "O nome do filme é obrigatório")
    private String nomeFilme;

    @NotBlank(message = "A data ou ano de lançamento é obrigatória")
    @Pattern(regexp = "^\\d{4}$", message = "O lançamento deve conter exatamente 4 números (ex: 2026)")
    private String lancamento;

    private double imdb; 

    @NotBlank(message = "O gênero é obrigatório")
    private String genero;

    @NotNull(message = "A duração é obrigatória")
    @Positive(message = "A duração deve ser maior que zero")
    private Integer duracao;

    @NotBlank(message = "A sinopse é obrigatória")
    private String sinopse;

    @NotBlank(message = "A URL do filme é obrigatória")
    @URL(message = "Insira uma URL válida")
    private String URL;
}