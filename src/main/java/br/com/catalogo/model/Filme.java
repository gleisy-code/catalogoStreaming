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
@Entity //entidade persistente
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//id gerado automaticamente
    private long id;
    private String nomeFilme;
    private String lancamento;
    private Integer imdb;
    private String genero;

    public Filme(long id, String nomeFilme, String lancamento, Integer imdb, String genero) {
        this.id = id;
        this.nomeFilme = nomeFilme;
        this.lancamento = lancamento;
        this.imdb = imdb;
        this.genero = genero;
    }
    
    public Filme(){
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

    public int getImdb() {
        return imdb;
    }

    public void setImdb(int imdb) {
        this.imdb = imdb;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
    
    
}
