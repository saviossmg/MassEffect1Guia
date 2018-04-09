package com.saviosvm.masseffect1guia.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.controller.JogadorAglomeradoC;
import com.saviosvm.masseffect1guia.controller.JogadorSistemaC;
import com.saviosvm.masseffect1guia.model.Aglomerado;
import com.saviosvm.masseffect1guia.model.Sistema;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkin extends Fragment {

    //banco e id
    private static JogadorAglomeradoC jogAgloBanco;
    private static JogadorSistemaC jogSistBanco;
    private static int idJogador;

    //Componentes visuais
    private ListView listaAglo = null;
    private ListView listaSist = null;

    //Componentes de controle
    private int clickAglomerado;
    private int clickSistema;
    private ArrayList<Sistema> sistemasJogador;
    private ArrayList<Aglomerado> aglomeradosJogador;

    //adapters
    private ArrayAdapter<Aglomerado> agloAdapter;
    private ArrayAdapter<Sistema> sistAdapter;

    // create boolean for fetching data
    private boolean isViewShown = false;

    public Checkin() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkin, container, false);

        //referencia visual
        listaAglo = (ListView) view.findViewById(R.id.checkin_listaAglo);
        listaSist = (ListView) view.findViewById(R.id.checkin_listaSist);

        listaCarregar();
        if (!aglomeradosJogador.isEmpty()) {
            //listview
            mudarAglomerado();
            mudarSistema(0);
        }
        listaAglo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickAglomerado = position;
                mudarSistema(position);
            }
        });
        listaSist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSistema = position;
                Toast.makeText(getActivity(), "Sistema " + sistAdapter.getItem(position) + " foi checado!", Toast.LENGTH_SHORT).show();
                realizarCheckin();
                atualizaContent();

            }
        });

        if (!isViewShown) {
            atualizaContent();
        }

        return view;
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        atualizaContent();
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            atualizaContent();
        } else {
            isViewShown = false;
        }
    }


    //metodo que vai carregar os dados do jogador
    private void listaCarregar(){
        //carrega primeiro os aglomerados e depois os sitemas
        try {
            //instancia o objeto
            aglomeradosJogador = new ArrayList<Aglomerado>();
            sistemasJogador = new ArrayList<Sistema>();

            aglomeradosJogador = jogAgloBanco.listarAgloJogador(idJogador);
            sistemasJogador = jogSistBanco.listarSisJogador(idJogador);
            filtraLista();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erro em TabLista: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo que vai filtrar o arryalist sistemas para saber a qual aglomerado ele pertence
    private void filtraLista() {
        //cria um array para armazenar os ids
        ArrayList<Integer> idsChecagem = new ArrayList<Integer>();
        ArrayList<Aglomerado> copia;
        int auxAglo = 0, auxId = 0;

        for (Aglomerado itemAglo : aglomeradosJogador) {
            //procura pelo ID do aglomerado
            for (Sistema itemSist : sistemasJogador) {
                if (itemSist.getIdAglomerado() == itemAglo.getId() && !itemSist.isPonto())
                    itemAglo.getAgloSistem().add(itemSist);
            }
            //armazena o id de um aglomerado que já foi totalmente checado
            if(itemAglo.getAgloSistem().isEmpty()){
                idsChecagem.add(itemAglo.getId());
            }
        }

        //remove aglomerados que estao completos
        if(idsChecagem.size() > 0){
            while(!idsChecagem.isEmpty()){
                copia = aglomeradosJogador;
                for(int i = 0; i < copia.size(); i++){
                    for(int p = 0; p < idsChecagem.size(); p++){
                        if(copia.get(i).getId() == idsChecagem.get(p)){
                            auxId = p;
                            auxAglo = i;
                        }
                    }
                }
                aglomeradosJogador.remove(auxAglo);
                idsChecagem.remove(auxId);
            }
        }

    }

    //modifica o adapter de sistemas
    private void mudarAglomerado() {
        // Important - Set the ChoiceMode
        agloAdapter = new ArrayAdapter<Aglomerado>(getActivity(), android.R.layout.simple_list_item_1, aglomeradosJogador) {
            //Sobrescreve o metodo de fonte
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.letra_check1));
                return view;
            }
        };
        listaAglo.setAdapter(agloAdapter);
    }

    //modifica o adapter de sistemas
    private void mudarSistema(int pos) {
        sistAdapter = new ArrayAdapter<Sistema>(getActivity(), android.R.layout.simple_list_item_1, aglomeradosJogador.get(pos).getAgloSistem()) {
            //Sobrescreve o metodo de fonte
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(getResources().getColor(R.color.letra_check2));
                return view;
            }
        };
        listaSist.setAdapter(sistAdapter);
        listaAglo.setSelection(0);
    }

    private void realizarCheckin(){
        jogSistBanco.vincular(aglomeradosJogador.get(clickAglomerado).getAgloSistem().get(clickSistema).getIdSisJogador());
        jogAgloBanco.vincular(aglomeradosJogador.get(clickAglomerado).getIdAgloJogador());
        aglomeradosJogador.get(clickAglomerado).getAgloSistem().remove(clickSistema);

    }

    private void atualizaContent(){
        listaCarregar();
        //zera o adapter se ele estiver vazio
        if (!aglomeradosJogador.isEmpty()) {
            mudarAglomerado();
            mudarSistema(0);
            //atualiza o status do adapter
            agloAdapter.notifyDataSetChanged();
            sistAdapter.notifyDataSetChanged();
        }else{
            agloAdapter = new ArrayAdapter<Aglomerado>(getActivity(), android.R.layout.simple_list_item_1, aglomeradosJogador) {};
            listaAglo.setAdapter(agloAdapter);
            sistAdapter = new ArrayAdapter<Sistema>(getActivity(), android.R.layout.simple_list_item_1,0) {};
            listaSist.setAdapter(sistAdapter);
        }
    }

    public static void validaBanco(JogadorAglomeradoC paranJogAglo, JogadorSistemaC paramJogSist, int paramId){
        jogAgloBanco = paranJogAglo;
        jogSistBanco = paramJogSist;
        idJogador = paramId;
    }

}
