/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gleisy
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String nomeUsuario;

    @Email(message = "Digite um e-mail válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotBlank(message = "O campo senha é obrigatório")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}$", 
        message = "A senha deve conter entre 6 e 20 caracteres, incluindo pelo menos uma letra e um número"
    )
    private String senha;
    
    private boolean planoAtivo;
    private boolean ehAdmin;
}