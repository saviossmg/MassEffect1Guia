package com.saviosvm.masseffect1guia.model;

import java.util.ArrayList;

/**
 * Created by savio on 12/10/2017.
 */

public class Aglomerado {

    //classe que guardará as informações sobre o aglomerado
    private int id;
    private int idAgloJogador;
    private String nome;
    private int totalSistemas;
    private boolean ponto;
    private ArrayList<Sistema> agloSistem;

    public Aglomerado() {
        this.id = id;
        this.idAgloJogador = idAgloJogador;
        this.nome = nome;
        this.totalSistemas = totalSistemas;
        this.ponto = ponto;
        this.agloSistem = new ArrayList<Sistema>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAgloJogador() {
        return idAgloJogador;
    }

    public void setIdAgloJogador(int idAgloJogador) {
        this.idAgloJogador = idAgloJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTotalSistemas() {
        return totalSistemas;
    }

    public void setTotalSistemas(int totalSistemas) {
        this.totalSistemas = totalSistemas;
    }

    public boolean isPonto() {
        return ponto;
    }

    public void setPonto(boolean ponto) {
        this.ponto = ponto;
    }

    public ArrayList<Sistema> getAgloSistem() {
        return agloSistem;
    }

    public void setAgloSistem(ArrayList<Sistema> agloSistem) {
        this.agloSistem = agloSistem;
    }

    public String toString(){
        return getNome();
    }
}
