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
 *
 * @author gleisy
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usuario_id;
    private String nomeUsuario;
    private String cpf;

    public Usuario(long usuario_id, String nomeUsuario, String cpf) {
        this.usuario_id = usuario_id;
        this.nomeUsuario = nomeUsuario;
        this.cpf = cpf;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
    
}
