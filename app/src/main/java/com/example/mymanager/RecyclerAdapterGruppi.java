package com.example.mymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapterGruppi extends RecyclerView.Adapter<RecyclerAdapterGruppi.MyViewHolder> {

    private List<List> list;
    private ItemClickListenerGruppi clicklistener;

    public RecyclerAdapterGruppi(List<List>list, ItemClickListenerGruppi clicklistener) {

        this.list=list;
        this.clicklistener = clicklistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_gruppi,parent,false);
        return new RecyclerAdapterGruppi.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.TextNomeGruppo.setText(list.get(position).get(1).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistener.onItemClick(list.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextNomeGruppo;

        public MyViewHolder(@NonNull View view) {
            super(view);
            TextNomeGruppo=view.findViewById(R.id.TextNomeGruppo);

        }
    }

    public interface ItemClickListenerGruppi{
        void onItemClick(List casi, int position);
    }
}
