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
 *registra cada vez que o usuario assiste um filme ou um epsodio assim como o momento em que ele parou de assistir
 * @author gleisy
 */
@Entity
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ondeParou;
    private boolean assistidoCompleto;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "idFilme")
    private Filme filme;
    @ManyToOne
    @JoinColumn(name = "idSerie")
    private Serie serie;

    public Historico(Long id, Integer ondeParou, boolean assistidoCompleto, Usuario usuario, Filme filme, Serie serie) {
        this.id = id;
        this.ondeParou = ondeParou;
        this.assistidoCompleto = assistidoCompleto;
        this.usuario = usuario;
        this.filme = filme;
        this.serie = serie;
    }

    public Historico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOndeParou() {
        return ondeParou;
    }

    public void setOndeParou(Integer ondeParou) {
        this.ondeParou = ondeParou;
    }

    public boolean isAssistidoCompleto() {
        return assistidoCompleto;
    }

    public void setAssistidoCompleto(boolean assistidoCompleto) {
        this.assistidoCompleto = assistidoCompleto;
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
