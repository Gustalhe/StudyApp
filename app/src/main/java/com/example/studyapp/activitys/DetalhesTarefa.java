package com.example.studyapp.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyapp.R;

public class DetalhesTarefa extends AppCompatActivity {

    private TextView tituloTarefa;
    private TextView descricaoTarefa;
    private TextView dataEntrega;
    private Button voltar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_tarefa);

        tituloTarefa = findViewById(R.id.tituloTarefa);
        descricaoTarefa = findViewById(R.id.descricaoTarefa);
        dataEntrega = findViewById(R.id.dataTarefa);
        voltar = findViewById(R.id.botaoVoltar);

        mostrarValores();
    }

    public void mostrarValores(){ //conectar com o banco, pegar o id do que foi selecionado e mostrar as informações
            tituloTarefa.setText(MainActivity.listaTarefa.get(MainActivity.posicao).getNome());
            descricaoTarefa.setText(MainActivity.listaTarefa.get(MainActivity.posicao).getDescricao());
            dataEntrega.setText(MainActivity.listaTarefa.get(MainActivity.posicao).getDataEntrega());

    }

    public void voltar(View view){
        finish();
    }


}
