package com.saviosvm.masseffect1guia.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saviosvm.masseffect1guia.model.Jogador;

import java.util.ArrayList;

/**
 * Created by savio on 12/10/2017.
 */

public class JogadorC {

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    private Jogador jogador;
    private Cursor cursor;

    public JogadorC(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getDatabase();
    }

    //LISTAR
    public ArrayList<Jogador> listar() {
        ArrayList<Jogador> result = new ArrayList<Jogador>();
        cursor = db.query("jogador", new String[]{"id", "nome", "genero"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            jogador = new Jogador();
            jogador.setId(cursor.getInt(0));
            jogador.setNome(cursor.getString(1));
            jogador.setGenero(cursor.getString(2));
            result.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    //INSERCAO
    public void inserir(Jogador dados) {
        ContentValues cv = new ContentValues();
        cv.put("nome", dados.getNome());
        cv.put("genero", dados.getGenero());
        db.insert("jogador", null, cv);
    }

    public void deletar(int id) {
        db.execSQL("DELETE FROM jogador WHERE  id =" + id);
    }

    //CONSULTA POR ID
    public Jogador findById(int id) {
        jogador = new Jogador();
        cursor = db.query("jogador", new String[]{"id", "nome", "genero"}, "id = " + id,
                null, null, null, null);

        cursor.moveToFirst();
        jogador.setId(cursor.getInt(0));
        jogador.setNome(cursor.getString(1));
        jogador.setGenero(cursor.getString(2));

        cursor.close();

        //retorna o objeto
        return jogador;
    }
}

