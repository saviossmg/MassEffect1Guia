package com.saviosvm.masseffect1guia.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saviosvm.masseffect1guia.model.Auxiliar;
import com.saviosvm.masseffect1guia.model.JogadorSistema;
import com.saviosvm.masseffect1guia.model.Sistema;

import java.util.ArrayList;

/**
 * Created by savio on 12/10/2017.
 */

public class JogadorSistemaC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Sistema sistema;
    private JogadorSistema jogadorSistema;
    private ArrayList<Sistema> sistemas;
    private ArrayList<JogadorSistema> jogadorSistemas;
    private ArrayList<Auxiliar> auxSistema;

    private Cursor cursor;


    public JogadorSistemaC(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
    }

    //LISTAR SISTEMAS
    public ArrayList<Sistema> listarSistemas() {
        sistemas = new ArrayList<Sistema>();
        cursor = db.query("sistema", new String[]{"id","nome", "idaglomerado"},  null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            sistema = new Sistema();
            sistema.setId(cursor.getInt(0));
            sistema.setNome(cursor.getString(1));
            sistema.setIdAglomerado(cursor.getInt(2));
            sistemas.add(sistema);
            cursor.moveToNext();
        }
        cursor.close();
        return sistemas;
    }

    //LISTAR SISTEMAS DO JOGADOR
    public ArrayList<JogadorSistema> listar(int id) {
        jogadorSistemas = new ArrayList<JogadorSistema>();
        cursor = db.query("JogadorSistema", new String[]{"id"}, "idjogador = "+id, null, null, null, null);
        cursor.moveToFirst();
        int teste = cursor.getCount();

        //vrifica se é nulo a primeira posição, se for ele vai criar os dados do jogador na tabela JogadorSistema
        //só vai chamar na hora de criar o jogador
        if(teste == 0){
            //chama o metodo para inserir no banco
            inserirJogadorSistema(id);
        }
        //faz de novo a consulta e pega os dados que foram inseridos
        cursor = db.query("JogadorSistema", new String[]{"id","idsistema", "idjogador","ponto"},"idjogador = "+id, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            jogadorSistema = new JogadorSistema();
            jogadorSistema.setId(cursor.getInt(0));
            jogadorSistema.setIdSistema(cursor.getInt(1));
            jogadorSistema.setIdJogador(cursor.getInt(2));
            jogadorSistema.setPonto(cursor.getInt(3));
            jogadorSistemas.add(jogadorSistema);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadorSistemas;
    }


    //LISTA SISTEMAS QUE ELE VISITOU
    public ArrayList<Sistema> listarSisJogador(int id){
        sistemas = new ArrayList<Sistema>();
        auxSistema = new ArrayList<Auxiliar>();

        cursor = db.query("jogadorsistema", new String[]{"id","ponto"},"idjogador = "+id, null, null, null, null);
        cursor.moveToFirst();

        //verifica se retornou vazio
        if(cursor.getCount() > 0){
            while(!cursor.isAfterLast()) {
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setId(cursor.getInt(0));
                auxiliar.setPonto(cursor.getInt(1));
                auxSistema.add(auxiliar);
                cursor.moveToNext();
            }

            //faz um laço que fara as querys dos sistemas visitados
            cursor = db.query("sistema", new String[]{"id","nome", "idaglomerado"},null,null,null,null,null);
            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                sistema = new Sistema();
                sistema.setId(cursor.getInt(0));
                sistema.setIdSisJogador(auxSistema.get(i).getId());
                sistema.setNome(cursor.getString(1));
                sistema.setIdAglomerado(cursor.getInt(2));
                if (auxSistema.get(i).getPonto() == 0)
                    sistema.setPonto(false);
                else
                    sistema.setPonto(true);

                sistemas.add(sistema);
                cursor.moveToNext();
                i++;
            }
        }
        return sistemas;
    }

    //INSERCAO QUANDO CRIA O JOGADOR
    public void inserirJogadorSistema(int id)
    {
        //primeiro os aglomerados
        sistemas = listarSistemas();
        //faz um foreach para preencher os dados do jogador na tabela
        ContentValues cv =new ContentValues();
        for(Sistema aux: sistemas){
            cv.put("idsistema", aux.getId());
            cv.put("idjogador",id);
            cv.put("ponto",0);
            db.insert("JogadorSistema", null, cv);
        }
    }

    //DELETA OS REGITROS QUANDO O JOGADOR É APAGADO
    public void deletarJogadorSistema(int id){
        db.execSQL("DELETE FROM jogadorsistema WHERE idjogador ="+id);
    }

    //DESVINCULAR
    public void desvincular(int id)
    {
        ContentValues cv = new ContentValues();
        cv.put("ponto",0);
        db.update("jogadorsistema", cv, "id ="+id, null);
    }

    //VINCULAR
    public void vincular(int idSistema)
    {
        ContentValues cv = new ContentValues();
        cv.put("ponto",1);
        db.update("jogadorsistema", cv, "id ="+idSistema, null);
    }

}
