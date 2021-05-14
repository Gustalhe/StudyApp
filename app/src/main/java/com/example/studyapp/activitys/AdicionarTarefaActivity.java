package com.example.studyapp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studyapp.R;
import com.example.studyapp.model.Tarefa;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private Button salvarBTT;
    private EditText nomeTarefa;
    private EditText descricaoTarefa;
    private int diaTarefa = 0;
    private int mesTarefa = 0;
    private int anoTarefa = 0;
    private Button salvar;
    private CalendarView calendarView;
    MainActivity mainActivity = new MainActivity();
    public static int controle = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_tarefa);

        salvarBTT = findViewById(R.id.salvarBT);
        nomeTarefa = findViewById(R.id.nomeET);
        descricaoTarefa = findViewById(R.id.descricaoET);
        calendarView = findViewById(R.id.calendarioV);
        salvar = findViewById(R.id.salvarBT);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int ano, int mes, int dia) {
                Toast.makeText(getApplicationContext(),"Data Selecionada: "+dia+"/"+(mes+1)+"/"+ano,Toast.LENGTH_SHORT).show();
                diaTarefa = dia;
                mesTarefa = (mes+1);
                anoTarefa = ano;
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    public void salvar(View view){

            boolean controll = true;

            if(nomeTarefa.getText().toString().isEmpty()){
                nomeTarefa.setHintTextColor(Color.RED);
                controll = false;
                Toast.makeText(getApplicationContext(),"Favor inserir um nome",Toast.LENGTH_SHORT).show();
            }
            if(descricaoTarefa.getText().toString().isEmpty()){
                descricaoTarefa.setHintTextColor(Color.RED);
                controll = false;
            }else if(controll == true){

                String dataEntrega = diaTarefa+"/"+mesTarefa+"/"+anoTarefa;

                if(diaTarefa == 0){
                    Date dataAutomaticaAtual = new Date(System.currentTimeMillis());
                    SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
                    dataEntrega = formatar.format(dataAutomaticaAtual);
                }


                Tarefa tarefa = new Tarefa();
                tarefa.setNome(nomeTarefa.getText().toString());
                tarefa.setDescricao(descricaoTarefa.getText().toString());
                tarefa.setDataEntrega(dataEntrega);

                SQLiteDatabase bancoDados = openOrCreateDatabase("banco",MODE_PRIVATE, null);
                String sql = "INSERT INTO tarefa(titulo, descricao, data) VALUES ('"+tarefa.getNome()+"', '"+tarefa.getDescricao()+"', '"+tarefa.getDataEntrega()+"')";
                bancoDados.execSQL(sql);
                bancoDados.close();
                finish();
                controle = 1;







            }

    }





}
