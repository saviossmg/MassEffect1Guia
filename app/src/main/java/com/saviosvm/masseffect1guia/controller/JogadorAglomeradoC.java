package com.saviosvm.masseffect1guia.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saviosvm.masseffect1guia.model.Aglomerado;
import com.saviosvm.masseffect1guia.model.Auxiliar;
import com.saviosvm.masseffect1guia.model.JogadorAglomerado;

import java.util.ArrayList;

/**
 * Created by savio on 12/10/2017.
 */

public class JogadorAglomeradoC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Aglomerado aglomerado;
    private JogadorAglomerado jogadorAglomerado;

    private ArrayList<Aglomerado> aglomerados;
    private ArrayList<JogadorAglomerado> jogadorAglomerados;
    private ArrayList<Auxiliar> auxAglomerado;

    private Cursor cursor;

    public JogadorAglomeradoC(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
    }

    //LISTAR AGLOMERADOS
    public ArrayList<Aglomerado> listarAglomerados() {
        aglomerados = new ArrayList<Aglomerado>();
        cursor = db.query("aglomerado", new String[]{"id", "nome", "totalsistemas"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            aglomerado = new Aglomerado();
            aglomerado.setId(cursor.getInt(0));
            aglomerado.setNome(cursor.getString(1));
            aglomerado.setTotalSistemas(cursor.getInt(2));
            aglomerados.add(aglomerado);
            cursor.moveToNext();
        }
        cursor.close();
        return aglomerados;
    }

    //LISTAR AGLOMERADOS DO JOGADOR
    public ArrayList<JogadorAglomerado> listar(int id) {
        jogadorAglomerados = new ArrayList<JogadorAglomerado>();
        cursor = db.query("jogadoraglomerado", new String[]{"id"}, "idjogador = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        int teste = cursor.getCount();

        //vrifica se é nulo a primeira posição, se for ele vai criar os dados do jogador na tabela JogadorAglomerados
        //só vai chamar na hora de criar o jogador
        if (teste == 0) {
            //chama o metodo para inserir no banco
            inserirJogadorAglomerado(id);
        }
        //faz de novo a consulta e pega os dados que foram inseridos
        cursor = db.query("jogadoraglomerado", new String[]{"id", "idaglomerado", "idjogador", "ponto"}, "idjogador = " + id, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            jogadorAglomerado = new JogadorAglomerado();
            jogadorAglomerado.setId(cursor.getInt(0));
            jogadorAglomerado.setIdAglomerado(cursor.getInt(1));
            jogadorAglomerado.setIdJogador(cursor.getInt(2));
            jogadorAglomerado.setPonto(cursor.getInt(3));
            jogadorAglomerados.add(jogadorAglomerado);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadorAglomerados;
    }

    //LISTA AGLOMERADOS QUE ELE VISITOU
    public ArrayList<Aglomerado> listarAgloJogador(int id) {
        aglomerados = new ArrayList<Aglomerado>();
        auxAglomerado = new ArrayList<Auxiliar>();

        cursor = db.query("jogadoraglomerado", new String[]{"id", "ponto"}, "idjogador = " + id, null, null, null, null);
        cursor.moveToFirst();

        //verifica se retornou vazio
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Auxiliar auxiliar = new Auxiliar();
                auxiliar.setId(cursor.getInt(0));
                auxiliar.setPonto(cursor.getInt(1));
                auxAglomerado.add(auxiliar);
                cursor.moveToNext();
            }

            //faz um laço que fara as querys dos sistemas visitados
            cursor = db.query("aglomerado", new String[]{"id", "nome", "totalsistemas"}, null, null, null, null, null);
            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                aglomerado = new Aglomerado();
                aglomerado.setId(cursor.getInt(0));
                aglomerado.setNome(cursor.getString(1));
                aglomerado.setIdAgloJogador(auxAglomerado.get(i).getId());
                aglomerado.setTotalSistemas(cursor.getInt(2));
                if (auxAglomerado.get(i).getPonto() == 0)
                    aglomerado.setPonto(false);
                else
                    aglomerado.setPonto(true);

                aglomerados.add(aglomerado);
                cursor.moveToNext();
                i++;
            }
        }
        return aglomerados;
    }


    //INSERCAO QUANDO CRIA O JOGADOR
    public void inserirJogadorAglomerado(int id) {
        //primeiro os aglomerados
        aglomerados = listarAglomerados();

        //faz um foreach para preencher os dados do jogador na tabela
        ContentValues cv = new ContentValues();

        for (Aglomerado aux : aglomerados) {
            cv.put("idaglomerado", aux.getId());
            cv.put("idjogador", id);
            cv.put("ponto", 0);
            db.insert("jogadoraglomerado", null, cv);
        }
    }

    //DELETA OS REGITROS QUANDO O JOGADOR É APAGADO
    public void deletarJogadorAglomerado(int id) {
//        db.delete("jogadoraglomerado", "idjogador =" + id, null);
        db.execSQL("DELETE FROM jogadoraglomerado WHERE idjogador =" + id);
    }

    //DESVINCULAR
    public void desvincular(int id) {
        ContentValues cv = new ContentValues();
        cv.put("ponto", 0);
        db.update("jogadoraglomerado", cv, "id =" + id, null);

    }

    //VINCULAR
    public void vincular(int id) {
        ContentValues cv = new ContentValues();
        cv.put("ponto", 1);
        db.update("jogadoraglomerado", cv, "id =" + id, null);

    }

}
