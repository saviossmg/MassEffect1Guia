package com.saviosvm.masseffect1guia.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.controller.JogadorAglomeradoC;
import com.saviosvm.masseffect1guia.controller.JogadorC;
import com.saviosvm.masseffect1guia.controller.JogadorSistemaC;
import com.saviosvm.masseffect1guia.model.Jogador;
import com.saviosvm.masseffect1guia.model.JogadorAglomerado;
import com.saviosvm.masseffect1guia.model.JogadorSistema;

import java.util.ArrayList;

public class Perfil extends Fragment implements View.OnClickListener {

    //banco e id
    private static JogadorC jogadoBanco;
    private static JogadorAglomeradoC jogAgloBanco;
    private static JogadorSistemaC jogSistBanco;
    private static int idJogador;

    //componentes visuais
    private ImageView fotoPerso = null;
    private TextView nomePerso = null;
    private TextView agloPerso = null;
    private TextView sistPerso = null;
    private TextView totalCompletado = null;
    private Button btnSair = null;
    private Button btnDeletar = null;
    private AlertDialog alerta;

    //aglomerados e sistemas
    private Jogador jogadorLogado;
    private ArrayList<JogadorAglomerado> jogadorAglomerados;
    private ArrayList<JogadorSistema> jogadorSistemas;

    private int sisVisi;
    private int agloVisi;
    private double total;

    private boolean isViewShown = false;

    public Perfil() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        fotoPerso = (ImageView) view.findViewById(R.id.perfil_perfilPerso);
        nomePerso = (TextView) view.findViewById(R.id.perfil_nomePerso);
        agloPerso = (TextView) view.findViewById(R.id.perfil_aglomExp);
        sistPerso = (TextView) view.findViewById(R.id.perfil_sistemasExp);
        totalCompletado = (TextView) view.findViewById(R.id.perfil_totalCompletado);

        //evento click dos botões, na fragment é diferente
        btnSair = (Button) view.findViewById(R.id.perfil_btnSair);
        btnDeletar = (Button) view.findViewById(R.id.perfil_btnDeletar);
        btnSair.setOnClickListener(this);
        btnDeletar.setOnClickListener(this);

        jogadorLogado = jogadoBanco.findById(idJogador);
        jogadorAglomerados = jogAgloBanco.listar(idJogador);
        jogadorSistemas = jogSistBanco.listar(idJogador);
        nomePerso.setText(jogadorLogado.getNome());

        if (jogadorLogado.getGenero().equals("M"))
            fotoPerso.setImageResource(R.drawable.msheppard);
        else if (jogadorLogado.getGenero().equals("F"))
            fotoPerso.setImageResource(R.drawable.fsheppard);

        if (!isViewShown) {
            jogadorAglomerados = jogAgloBanco.listar(idJogador);
            jogadorSistemas = jogSistBanco.listar(idJogador);
            carregaEstatisticas();
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        //eventos de click
        switch (v.getId()) {
            case R.id.perfil_btnSair:
                perfilSair();
                break;
            case R.id.perfil_btnDeletar:
                botaoDeletar();
                break;
        }
    }

    /*
    @Override
    public void onResume() {
        super.onResume();
        jogadorAglomerados = jogAgloBanco.listar(idJogador);
        jogadorSistemas = jogSistBanco.listar(idJogador);
        carregaEstatisticas();
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isViewShown = true;
            jogadorAglomerados = jogAgloBanco.listar(idJogador);
            jogadorSistemas = jogSistBanco.listar(idJogador);
            carregaEstatisticas();
        } else {
            isViewShown = false;
        }
    }

    //filtra as estatisticas para mostrar no label
    private void carregaEstatisticas(){
        sisVisi = 0;
        agloVisi = 0;
        for(JogadorAglomerado aux: jogadorAglomerados){
            if(aux.getPonto() == 1)
                agloVisi++;
        }
        for(JogadorSistema aux: jogadorSistemas){
            if(aux.getPonto() == 1)
                sisVisi++;
        }
        //seta o texto
        if (sisVisi > 0)
            total = (sisVisi * 100) / jogadorSistemas.size();
        else
            total = 0;

        agloPerso.setText("Aglomerados: " + agloVisi + " de " + jogadorAglomerados.size());
        sistPerso.setText("Sistemas: " + sisVisi + " de " + jogadorSistemas.size());
        totalCompletado.setText("Completado: " + total + " %");

    }


    //Evento do botão de deletar personagem
    private void botaoDeletar() {
        alertaDeletar();
    }

    //metodo que vai chamar o alert dialog para chamar o deletaplayer
    private void alertaDeletar() {
        //sobrescreve o listener
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deletarPlayer();
                        Toast.makeText(getActivity().getApplicationContext(), "Jogador apagado com sucesso!", Toast.LENGTH_SHORT).show();
                        //cria a intenção
                        Intent resultado = new Intent();
                        //Seta o valor do resultado e os que serao retornados
                        getActivity().setResult(Activity.RESULT_OK, resultado);
                        //volta para a activity anterior
                        getActivity().finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(getActivity().getApplicationContext(), "Não apagou  o jogador!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        //define o titulo
        builder.setTitle("Atenção");
        //define a mensagem
        builder.setMessage("Deseja realmente excluir esse player ? Os dados não poderão ser recuperados...");
        //define um botão como negativo
        builder.setNegativeButton("Não", dialogClickListener);
        //define um botão como positivo.
        builder.setPositiveButton("Sim", dialogClickListener);
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
        //muda a cor das letras
        alerta.getButton(alerta.BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 0, 0));
        alerta.getButton(alerta.BUTTON_POSITIVE).setTextColor(Color.rgb(255, 0, 0));
    }

    //Metodo que vai deletar o player
    private void deletarPlayer() {
        try {
            //deleta na ordem
            jogSistBanco.deletarJogadorSistema(idJogador);
            jogAgloBanco.deletarJogadorAglomerado(idJogador);
            jogadoBanco.deletar(idJogador);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Deu ruim: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Evento do botão de sair
    private void perfilSair() {
        //cria a intenção
        Intent resultado = new Intent();
        //Seta o valor do resultado e os que serao retornados
        getActivity().setResult(Activity.RESULT_OK, resultado);
        //volta para a activity anterior
        getActivity().finish();
    }

    public static void validaBanco(JogadorC paramJogador, JogadorAglomeradoC paranJogAglo,
                                   JogadorSistemaC paramJogSist, int paramId) {

        jogadoBanco = paramJogador;
        jogAgloBanco = paranJogAglo;
        jogSistBanco = paramJogSist;
        idJogador = paramId;

    }


}
