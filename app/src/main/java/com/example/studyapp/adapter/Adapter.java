package com.example.studyapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studyapp.R;
import com.example.studyapp.model.Tarefa;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private List<Tarefa> listaTarefa;

    public Adapter(List<Tarefa> lista ){
        this.listaTarefa = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista, parent, false);

        return new MyViewHolder(itemLista);

        //é chamada para que seja possivel criar as visualizações
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //exibe todos itens e recupera os dados
        Tarefa tarefa = listaTarefa.get(position);
        holder.titulo.setText(tarefa.getNome());
        holder.data.setText(tarefa.getDataEntrega());
        holder.preDescricao.setText(tarefa.getDescricao());

    }

    @Override
    public int getItemCount() {
        //retorna a quantidade de itens vão ser exibidos
        return listaTarefa.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //esta classe vai ser responsavel por guardar cada um dos dados antes deles serem exibidos na tela


        TextView titulo;
        TextView data;
        TextView preDescricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tituloTarefaAdapter);
            data = itemView.findViewById(R.id.dataTarefaAdapter);
            preDescricao = itemView.findViewById(R.id.descricaoTarefaAdapter);

        }
    }

}
