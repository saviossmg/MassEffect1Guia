package com.saviosvm.masseffect1guia.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.controller.JogadorC;
import com.saviosvm.masseffect1guia.model.Jogador;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    //referencias visuais
    private ImageView fotoPerso = null;
    private Spinner selecaoJogador = null;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter<String> spinnerArrayAdapter;

    //referencias de controlers e models
    private Jogador jogador = null;
    private JogadorC jogadoroBanco;

    private ArrayList<Jogador> jogadores = null;
    private ArrayList<String> nomeJog = null;

    private int posAdapter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        jogadoroBanco = new JogadorC(getApplicationContext());
        selecaoJogador = (Spinner) findViewById(R.id.login_spnSelecaoperso);
        fotoPerso = (ImageView) findViewById(R.id.login_imgPerso);

        //Carrega lista com os players
        carregarPlayers();
        criarAdapter();

        //verifica se vem vazio para setar a foto
        if (jogadores.isEmpty())
            fotoPerso.setImageResource(R.drawable.n7logo);
        selecaoJogador.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView selecaoJogador, View v, int posicao, long id) {
                //pega nome pela posição
                String nome = selecaoJogador.getItemAtPosition(posicao).toString();
                posAdapter = posicao;
                if (jogadores.get(posicao).getGenero().equals("M")) {
                    fotoPerso.setImageResource(R.drawable.msheppard);
                } else if (jogadores.get(posicao).getGenero().equals("F")) {
                    fotoPerso.setImageResource(R.drawable.fsheppard);
                } else {
                    fotoPerso.setImageResource(R.drawable.n7logo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void clickCriarPersonagem(View Botao) {

        //cria uma intenção e solicita a troca de telas
        Intent solicitacao = new Intent(this, CriarPersonagem.class);

        //Dicionario de dados
        CriarPersonagem.validaBanco(jogadoroBanco);
        startActivityForResult(solicitacao, 1);
    }
    public void clickSobre(View Botao) {

        //cria uma intenção e solicita a troca de telas
        Intent solicitacao = new Intent(this, Sobre.class);

        //Dicionario de dados
        startActivityForResult(solicitacao, 3);
    }

    public void clickEntrar(View Botao) {
        if (jogadores.isEmpty()) {
            Toast.makeText(this, "Não há jogadores registrados.", Toast.LENGTH_SHORT).show();
        } else {
            //Dicionario de dados
            Bundle dicionario = new Bundle();
            dicionario.putInt("id", jogadores.get(posAdapter).getId());

            Menu.validaBanco(jogadoroBanco,jogadores.get(posAdapter).getId());

            //cria uma intenção e solicita a troca de telas
            Intent solicitacao = new Intent(this, Menu.class);
            //Dicionario de dados
            solicitacao.putExtras(dicionario);
            startActivityForResult(solicitacao, 2);
        }

    }

    //metodo que vai carregar a lista de players
    private void carregarPlayers(){
        //cria a lista com os jogadores ja no sistema
        jogadores = new ArrayList<Jogador>();
        nomeJog = new ArrayList<String>();
        try {
            //carrega a lista de jogadores
            jogadores = jogadoroBanco.listar();
            //carrega o dropbox
            for(Jogador item : jogadores){
                nomeJog.add(item.getNome());
            }
        } catch (Exception e) {
            //tratamento de erro
            Toast.makeText(this, "Deu ruim: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    //recarrega o adapter
    private void criarAdapter() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomeJog);
        spinnerArrayAdapter = arrayAdapter;
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        selecaoJogador.setAdapter(spinnerArrayAdapter);
        spinnerArrayAdapter.notifyDataSetChanged();
        posAdapter = 0;
    }

    @Override
    public void onActivityResult(int codigo, int resultado, Intent dados) {
        try {
            carregarPlayers();
            criarAdapter();
            //verifica se vem vazio para setar a foto
            if (jogadores.isEmpty())
                fotoPerso.setImageResource(R.drawable.n7logo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}