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
 *Guarda os dados de cada episódio e aponta para a série correspondente
 * @author gleisy
 */
@Entity
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numeroEp;
    private Integer temporada;
    private String titulo;
    private Integer duracao;
    private String URLvideo;
    @ManyToOne
    @JoinColumn(name = "idSerie")
    private Serie serie;//varios eps para uma serie

    public Episodio() {
    }

    public Episodio(Long id, Integer numeroEp, Integer temporada, String titulo, Integer duracao, String URLvideo, Serie serie) {
        this.id = id;
        this.numeroEp = numeroEp;
        this.temporada = temporada;
        this.titulo = titulo;
        this.duracao = duracao;
        this.URLvideo = URLvideo;
        this.serie = serie;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroEp() {
        return numeroEp;
    }

    public void setNumeroEp(Integer numeroEp) {
        this.numeroEp = numeroEp;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getURLvideo() {
        return URLvideo;
    }

    public void setURLvideo(String URLvideo) {
        this.URLvideo = URLvideo;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
    
    
}
