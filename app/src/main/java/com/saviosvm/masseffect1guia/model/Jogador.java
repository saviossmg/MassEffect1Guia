package com.saviosvm.masseffect1guia.model;

/**
 * Created by savio on 12/10/2017.
 */

public class Jogador {

    //classe modelo que guarda informações relevantes do player
    private int id;
    private String nome;
    private String genero;

    public Jogador() {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String toString(){
        return getNome();
    }


}
