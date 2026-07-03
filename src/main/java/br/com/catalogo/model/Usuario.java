/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

/**
 *controla dados de login e se o plano está ativo ou não referido a um usuario
 * @author gleisy
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuario_id;
    private String nomeUsuario;
    private String email;
    private String senha;
    private boolean planoAtivo;
    private boolean ehAdmin; // true se for administrador oficial, false se for usuário comum
    private LocalDate dataAtivacaoPlano; // guarda o dia em que o plano foi pago
    

    public Usuario(){
    }

    public Usuario(Long usuario_id, String nomeUsuario, String email, String senha, boolean planoAtivo, boolean ehAdmin, LocalDate dataAtivacaoPlano) {
        this.usuario_id = usuario_id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.planoAtivo = planoAtivo;
        this.ehAdmin = ehAdmin;
        this.dataAtivacaoPlano = dataAtivacaoPlano;
    }

    
    
    

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPlanoAtivo() {
        return planoAtivo;
    }

    public void setPlanoAtivo(boolean planoAtivo) {
        this.planoAtivo = planoAtivo;
    }

    public boolean isEhAdmin() {
        return ehAdmin;
    }

    public void setEhAdmin(boolean ehAdmin) {
        this.ehAdmin = ehAdmin;
    }

    public LocalDate getDataAtivacaoPlano() {
        return dataAtivacaoPlano;
    }

    public void setDataAtivacaoPlano(LocalDate dataAtivacaoPlano) {
        this.dataAtivacaoPlano = dataAtivacaoPlano;
    }
    
    
    
    
}
