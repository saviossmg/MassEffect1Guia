package com.saviosvm.masseffect1guia.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.model.Aglomerado;
import com.saviosvm.masseffect1guia.view.activity.Menu;

import java.util.ArrayList;

/**
 * Created by savio on 28/02/2018.
 */

public class AglomeradoA extends ArrayAdapter<Aglomerado>{

    Menu copia = null;
    boolean tipo;

    public AglomeradoA (Menu contexto, ArrayList<Aglomerado> vetAglo, boolean user){
            super(contexto, 0, vetAglo);
            copia = contexto;
            tipo = user;
    }

    public View getView(int posicao, View celulaReciclado, ViewGroup pai){
        //CRIAR UMA CELULA PARA CADA ELEMENTO DO VETOR DE CARROS
        //PREENCHER ESSA CELULA COM OS DADOS DO VETOR DE CARROS NA POSICAO posicao
        Aglomerado c = this.getItem(posicao);

        if(celulaReciclado == null)
        {
            celulaReciclado = LayoutInflater.from(getContext()).inflate(R.layout.celula_aglomerado, pai,false);
        }

        //pegar os dados do objeto e setar os elementos visuais da celula
        TextView nome = (TextView)celulaReciclado.findViewById(R.id.celaglo_nome);
        TextView total = (TextView)celulaReciclado.findViewById(R.id.celaglo_total);

        //seta os dados
        nome.setText(c.getNome());
        //verifica o tipo
        String tt = "";
        int visitados = c.getAgloSistem().size();
        int todos = c.getTotalSistemas();
        if(tipo)
            tt = "Visitados: "+ visitados+" de "+todos;
        else
            tt = "faltam: "+ visitados+" de "+todos;

        return celulaReciclado;

    }

}
