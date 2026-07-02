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
import jakarta.persistence.OneToMany;
import java.util.List;

/**
 *Guarda as informações gerais da série e gerencia a lista de episódios dela
 * @author gleisy
 */
@Entity
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String genero;
    private Double imbd;
    private String sinopse ;
    private Integer QuantTemporadas;
    @OneToMany
    @JoinColumn(name = "epsodios")
    List<Episodio> epsodios;//@OneToMany

    public Serie() {
    }

    public Serie(Long id, String titulo, String genero, Double imbd, String sinopse, Integer QuantTemporadas, List<Episodio> epsodios) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.imbd = imbd;
        this.sinopse = sinopse;
        this.QuantTemporadas = QuantTemporadas;
        this.epsodios = epsodios;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getImbd() {
        return imbd;
    }

    public void setNotaImbd(Double imbd) {
        this.imbd = imbd;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Integer getQuantTemporadas() {
        return QuantTemporadas;
    }

    public void setQuantTemporadas(Integer QuantTemporadas) {
        this.QuantTemporadas = QuantTemporadas;
    }

    public List<Episodio> getEpsodios() {
        return epsodios;
    }

    public void setEpsodios(List<Episodio> epsodios) {
        this.epsodios = epsodios;
    }
    
    
}
