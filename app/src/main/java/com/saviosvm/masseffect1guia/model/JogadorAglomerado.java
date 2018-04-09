package com.saviosvm.masseffect1guia.model;

import java.util.ArrayList;

/**
 * Created by savio on 12/10/2017.
 */

public class JogadorAglomerado {

    private int id;
    private int idJogador;
    private int idAglomerado;
    private int ponto;
    private ArrayList<Sistema> agloSistem;

    public JogadorAglomerado() {
        this.id = id;
        this.idJogador = idJogador;
        this.idAglomerado = idAglomerado;
        this.ponto = ponto;
        this.agloSistem = agloSistem;
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

    public int getIdAglomerado() {
        return idAglomerado;
    }

    public void setIdAglomerado(int idAglomerado) {
        this.idAglomerado = idAglomerado;
    }

    public int getPonto() {
        return ponto;
    }

    public void setPonto(int ponto) {
        this.ponto = ponto;
    }

    public ArrayList<Sistema> getAgloSistem() {
        return agloSistem;
    }

    public void setAgloSistem(ArrayList<Sistema> agloSistem) {
        this.agloSistem = agloSistem;
    }
}
