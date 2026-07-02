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
 *aqui estaram os filmes que o usuario favoritou para ver depois
 * @author gleisy
 */
@Entity
public class MinhaLista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; 
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;//ManyToOne
    @ManyToOne
    @JoinColumn(name = "idFilme")
    private Filme filme;//ManyToOne
    @ManyToOne
    @JoinColumn(name = "idSerie")
    private Serie serie;//ManyToOne

    public MinhaLista(long id, Usuario usuario, Filme filme, Serie serie) {
        this.id = id;
        this.usuario = usuario;
        this.filme = filme;
        this.serie = serie;
    }

    public MinhaLista() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    
    
    
}
