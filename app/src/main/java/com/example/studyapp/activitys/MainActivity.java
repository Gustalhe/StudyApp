package com.example.studyapp.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.studyapp.R;
import com.example.studyapp.RecyclerItemClickListener;
import com.example.studyapp.adapter.Adapter;
import com.example.studyapp.model.Tarefa;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    public RecyclerView recyclerView;
    public static List<Tarefa>listaTarefa = new ArrayList<>();
    public static int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);


        try{
            //criar banco de dados ou abrir "openORcreate"
            SQLiteDatabase bancoDados = openOrCreateDatabase("banco",MODE_PRIVATE, null);

            
            //criar tabela
            String sql = "CREATE TABLE IF NOT EXISTS tarefa (cod INTEGER PRIMARY KEY AUTOINCREMENT , titulo VARCHAR, descricao VARCHAR, data VARCHAR)";
            bancoDados.execSQL(sql);

            //recuperar dados da tabela
            String consulta = "SELECT cod, titulo, descricao, data FROM tarefa";

            Cursor cursor = bancoDados.rawQuery(consulta,null);

            //indices da tabela
            int indiceNome = cursor.getColumnIndex("titulo");
            int indiceDescricao = cursor.getColumnIndex("descricao");
            int indiceData = cursor.getColumnIndex("data");
            int indiceCod = cursor.getColumnIndex("cod");


            cursor.moveToFirst();
            for(int i = 1; i <= cursor.getCount(); i++){

                Tarefa tarefa = new Tarefa();

                //System.out.println("codigo banco: "+cursor.getString(indiceCod));
                tarefa.setNome(cursor.getString(indiceNome));
                tarefa.setDescricao(cursor.getString(indiceDescricao));
                tarefa.setDataEntrega(cursor.getString(indiceData));
                tarefa.setCod(cursor.getInt(indiceCod));

                    listaTarefa.add(tarefa);




                cursor.moveToNext();

            }
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
            
        }

        //Listagem de tarefas
        //configurar adapter (adapta os dados para serem exibidos na lista(mais importante))
         Adapter adapter = new Adapter(listaTarefa);

        //configurar recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        //eventos de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                posicao = position;
                                startActivity(new Intent(MainActivity.this, DetalhesTarefa.class));
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                posicao = position;
                                abrirDialog();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }



    public void adicionar(View view){
        startActivity(new Intent(MainActivity.this, AdicionarTarefaActivity.class));
    }

    public void abrirDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Excluindo a tarefa "+listaTarefa.get(posicao).getNome());
        dialog.setMessage("Confirma esta ação?");
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Tarefa tarefa  = listaTarefa.get(posicao);
                Toast.makeText(getApplicationContext(),"Atividade Excluida com êxito!",Toast.LENGTH_SHORT).show();

                //reabrindo banco de dados p/ excluir
                SQLiteDatabase bancoDados = openOrCreateDatabase("banco",MODE_PRIVATE, null);
                String sqlExcluir = "DELETE FROM tarefa WHERE cod ="+tarefa.getCod();
                bancoDados.execSQL(sqlExcluir);
                bancoDados.close();
                atualizarListaDelete();
                //por ultimo remover da recycler view
                listaTarefa.remove((posicao));
            }
        });

        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.create();
        dialog.show();
    }
    //atualizar recyclerview quando excluir uma atividade8
    public void atualizarListaDelete(){ Adapter adapter = new Adapter(listaTarefa);recyclerView.setAdapter(adapter); }


    @Override
    protected void onStart() {
        super.onStart();

        //atualizar a lista quando inserir mais uma atividade somente

        if(AdicionarTarefaActivity.controle == 1){
            SQLiteDatabase bancoDados = openOrCreateDatabase("banco",MODE_PRIVATE, null);
            String consulta = "SELECT cod, titulo, descricao, data FROM tarefa";
            Cursor cursor = bancoDados.rawQuery(consulta,null);

            //indices da tabela
            int indiceNome = cursor.getColumnIndex("titulo");
            int indiceDescricao = cursor.getColumnIndex("descricao");
            int indiceData = cursor.getColumnIndex("data");
            int indiceCod = cursor.getColumnIndex("cod");


            cursor.moveToLast();


            Tarefa tarefa = new Tarefa();

            //System.out.println("codigo banco: "+cursor.getString(indiceCod));
            tarefa.setNome(cursor.getString(indiceNome));
            tarefa.setDescricao(cursor.getString(indiceDescricao));
            tarefa.setDataEntrega(cursor.getString(indiceData));
            tarefa.setCod(cursor.getInt(indiceCod));
            bancoDados.close();

            listaTarefa.add(tarefa);

            Adapter adapter = new Adapter(listaTarefa);
            recyclerView.setAdapter(adapter);
            AdicionarTarefaActivity.controle = 0;
        }



    }







    }
