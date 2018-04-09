package com.saviosvm.masseffect1guia.model;

/**
 * Created by savio on 12/10/2017.
 */

public class JogadorSistema {

    private int id;
    private int idJogador;
    private int idSistema;
    private int ponto;

    public JogadorSistema() {
        this.id = id;
        this.idJogador = idJogador;
        this.idSistema = idSistema;
        this.ponto = ponto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }

    public int getPonto() {
        return ponto;
    }

    public void setPonto(int ponto) {
        this.ponto = ponto;
    }
}
