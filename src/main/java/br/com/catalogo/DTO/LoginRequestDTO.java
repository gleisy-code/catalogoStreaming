/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 *
 * @author gleisy
 */
@Data
public class LoginRequestDTO {
	@Schema(
            description = "Nome de usuário utilizado no login",
            example = "admin"
    )
    private String username;
	@Schema(
            description = "Senha do usuário",
            example = "142536"
    )
    private String password;
}
