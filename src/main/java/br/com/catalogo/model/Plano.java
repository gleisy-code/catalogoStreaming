/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.catalogo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author gleisy
 */
@Entity
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPlano;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")    
    private Usuario usuario;
    private String tipoPlano;

    public Plano(long idPlano, Usuario usuario, String tipoPlano) {
        this.idPlano = idPlano;
        this.usuario = usuario;
        this.tipoPlano = tipoPlano;
    }
    public Plano(){
    }

    public long getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(long idPlano) {
        this.idPlano = idPlano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(String tipoPlano) {
        this.tipoPlano = tipoPlano;
    }
    
    
    
    
    
}
