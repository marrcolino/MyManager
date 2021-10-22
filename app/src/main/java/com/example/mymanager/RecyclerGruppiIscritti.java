package com.example.mymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerGruppiIscritti extends RecyclerView.Adapter<RecyclerGruppiIscritti.ViewHolder> {

    private List<List> list;
    private LayoutInflater mInflater;


    public RecyclerGruppiIscritti(Context context , List<List>list) {
        this.mInflater = LayoutInflater.from(context);
        this.list=list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_row_gruppi, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_gruppi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.TextNomeGruppo.setText(list.get(position).get(1).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TextNomeGruppo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextNomeGruppo = itemView.findViewById(R.id.TextNomeGruppo);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
