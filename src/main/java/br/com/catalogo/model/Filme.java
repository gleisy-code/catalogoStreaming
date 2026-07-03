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
 *dados dos filmes e link de vídeo
 * @author gleisy
 */
@Entity //entidade persistente
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id gerado automaticamente
    private Long id;
    private String nomeFilme;
    private String lancamento;
    private double imdb;
    private String genero;
    private Integer duracao;
    private String sinopse;
    private String URL;

    public Filme(Long id, String nomeFilme, String lancamento, double imdb, String genero, Integer duracao, String sinopse, String URL) {
        this.id = id;
        this.nomeFilme = nomeFilme;
        this.lancamento = lancamento;
        this.imdb = imdb;
        this.genero = genero;
        this.duracao = duracao;
        this.sinopse = sinopse;
        this.URL = URL;
    }
    

   
    
    public Filme(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public double getImdb() {
        return imdb;
    }

    public void setImdb(double imdb) {
        this.imdb = imdb;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    
    
    
}
