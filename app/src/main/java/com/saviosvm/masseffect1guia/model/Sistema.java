package com.saviosvm.masseffect1guia.model;

/**
 * Created by savio on 12/10/2017.
 */

public class Sistema {

    private int id;
    private int idSisJogador;
    private int idAglomerado;
    private boolean ponto;
    private String nome;

    public Sistema() {
        this.id = id;
        this.idSisJogador = idSisJogador;
        this.idAglomerado = idAglomerado;
        this.ponto = ponto;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSisJogador() {
        return idSisJogador;
    }

    public void setIdSisJogador(int idSisJogador) {
        this.idSisJogador = idSisJogador;
    }

    public int getIdAglomerado() {
        return idAglomerado;
    }

    public void setIdAglomerado(int idAglomerado) {
        this.idAglomerado = idAglomerado;
    }

    public boolean isPonto() {
        return ponto;
    }

    public void setPonto(boolean ponto) {
        this.ponto = ponto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return getNome();
    }
}
