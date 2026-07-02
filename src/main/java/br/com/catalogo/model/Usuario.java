/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *controla dados de login e se o plano está ativo ou não referido a um usuario
 * @author gleisy
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usuario_id;
    private String nomeUsuario;
    private String email;
    private String senha;
    private boolean planoAtivo;

    public Usuario(long usuario_id, String nomeUsuario, String email, String senha, boolean planoAtivo) {
        this.usuario_id = usuario_id;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.planoAtivo = planoAtivo;
    }

    public Usuario(){
    }

    public long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(long usuario_id) {
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
    
    
    
    
}
