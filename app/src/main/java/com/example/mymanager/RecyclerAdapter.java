package com.example.mymanager;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    private List<Casi> list;
    private ItemClickListener clicklistener;


    public RecyclerAdapter(List<Casi>list, ItemClickListener clicklistener){

        this.list=list;
        this.clicklistener = clicklistener;
    }


    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,parent,false);
        //MyViewHolder myViewHolder = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        //holder.Id_input.setText(String.valueOf(list.get(position).getCasi()));
        //holder.TextId.setText(list.get(position).getId());
        /*Cursor cursor = MainActivity.DB.listaCasiDiStudio(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                holder.TextCasodistudio.setText(cursor.getString(0));
                holder.TextCorso.setText(cursor.getString(3));
                holder.TextNomeprof.setText(cursor.getString(4));
            }
        }*/
        holder.TextCasodistudio.setText(list.get(position).getNome());
        holder.TextCorso.setText(list.get(position).getEsame());
        holder.TextNomeprof.setText(list.get(position).getMatricolaProfessore());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistener.onItemClick(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextId;
        TextView TextCasodistudio;
        TextView TextCorso;
        TextView TextNomeprof;
        public MyViewHolder(@NonNull View view) {
            super(view);
            TextId=view.findViewById(R.id.TextId);
            TextCasodistudio=view.findViewById(R.id.TextCasodistudio);
            TextCorso=view.findViewById(R.id.TextCorso);
            TextNomeprof=view.findViewById(R.id.TextNomeprof);
        }
    }

    public interface ItemClickListener{
        void onItemClick(Casi casi);
    }
}
