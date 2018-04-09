package com.saviosvm.masseffect1guia.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.saviosvm.masseffect1guia.R;
import com.saviosvm.masseffect1guia.controller.JogadorC;
import com.saviosvm.masseffect1guia.model.Jogador;

import java.io.IOException;

public class CriarPersonagem extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private Jogador novoPlayer = null;
    private ImageView imgSexo = null;
    private EditText nome = null;
    private RadioGroup genero = null;

    static JogadorC jogAgloBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_personagem);

        //Busca a referencia para o objeto atraves da classe R
        imgSexo = (ImageView) findViewById(R.id.criar_imgPerso);
        nome = (EditText) findViewById(R.id.criar_nomePerso);
        genero = (RadioGroup) findViewById(R.id.criar_genero);
        genero.setOnCheckedChangeListener(this);
        //cria o novo objeto
        novoPlayer = new Jogador();
        novoPlayer.setGenero("M");

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        //muda a foto do personagem e tambem a opção de sexo
        if (checkedId == R.id.criar_m) {
            imgSexo.setImageResource(R.drawable.msheppard);
            novoPlayer.setGenero("M");
        } else if (checkedId == R.id.criar_f) {
            imgSexo.setImageResource(R.drawable.fsheppard);
            novoPlayer.setGenero("F");
        }
    }

    //metodo do botão de salvar o personagem
    public void salvarPersonagem(View Botao) throws IOException {
        if (nome.getText().length() <= 0) {
            //retorna uma mensagem caso o campo de nome esteja vazio
            Toast.makeText(this, "Insira um nome para seu personagem.", Toast.LENGTH_SHORT).show();
        } else {
            //seta o nome do player no objeto
            novoPlayer.setNome(nome.getText().toString());
            //chama o metodo de salvar
            salvar();
        }
    }

    //metodo que vai salvar o personagem no arquivo
    private void salvar() throws IOException {
        try {
            //persiste
            jogAgloBanco.inserir(novoPlayer);
            nome.setText("");
            Toast.makeText(this, "Player adicionado com sucesso", Toast.LENGTH_SHORT).show();
            //cria a intenção
            Intent resultado = new Intent();
            //Seta o valor do resultado e os que serao retornados
            setResult(Activity.RESULT_OK, resultado);
            //volta para a activity anterior
            finish();
        } catch (Exception e) {
            //tratamento de erro
            Toast.makeText(this, "Deu ruim: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static void validaBanco(JogadorC paramJogador) {
        jogAgloBanco = paramJogador;
    }
}
