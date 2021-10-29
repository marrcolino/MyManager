package com.example.mymanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerGruppiIscritti extends RecyclerView.Adapter<RecyclerGruppiIscritti.ViewHolder> {

    private List<List> listInfo;
    private List<List> list;
    private LayoutInflater mInflater;
    int positionInfo0 = 0;
    int positionInfo1 = 1;
    int positionInfo2 = 2;
    int positionInfo3 = 3;


    public RecyclerGruppiIscritti(Context context , List<List>list, List<List>listInfo) {
        this.mInflater = LayoutInflater.from(context);
        this.list=list;
        this.listInfo=listInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_row_info_gruppi_prof, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_gruppi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textViewNomeGruppo.setText(list.get(position).get(1).toString());
        listInfo = new ArrayList<>();
        holder.listaFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),Lista_file_allegati.class);
                v.getContext().startActivity(i);
            }
        });

        for(int i = 2; i<6; i++)
        {
            if(list.get(position).get(i).toString().equals("vuoto"))
            {
                List<String> arrlistInfo = new ArrayList<String>();
                arrlistInfo.add(" ");
                arrlistInfo.add(" ");
                arrlistInfo.add(" ");
                listInfo.add(arrlistInfo);
            }
            else
            {
                Cursor cursorInfo = MainActivity.DB.listaInfoStudentiGruppiIscritti(list.get(position).get(i).toString());
                if (cursorInfo.getCount() > 0) {
                    while (cursorInfo.moveToNext()) {
                        List<String> arrlistInfo = new ArrayList<String>();
                        arrlistInfo.add(cursorInfo.getString(0));
                        arrlistInfo.add(cursorInfo.getString(1));
                        arrlistInfo.add(cursorInfo.getString(2));
                        listInfo.add(arrlistInfo);
                    }
                }
            }
        }

        holder.textViewPart1.setText(list.get(position).get(2).toString() + " " + listInfo.get(positionInfo0).get(0).toString()+ " " + listInfo.get(positionInfo0).get(1).toString()+ " " + listInfo.get(positionInfo0).get(2).toString());
        holder.textViewPart2.setText(list.get(position).get(3).toString() + " " + listInfo.get(positionInfo1).get(0).toString()+ " " + listInfo.get(positionInfo1).get(1).toString()+ " " + listInfo.get(positionInfo1).get(2).toString());
        holder.textViewPart3.setText(list.get(position).get(4).toString() + " " + listInfo.get(positionInfo2).get(0).toString()+ " " + listInfo.get(positionInfo2).get(1).toString()+ " " + listInfo.get(positionInfo2).get(2).toString());
        holder.textViewPart4.setText(list.get(position).get(5).toString() + " " + listInfo.get(positionInfo3).get(0).toString()+ " " + listInfo.get(positionInfo3).get(1).toString()+ " " + listInfo.get(positionInfo3).get(2).toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewNomeGruppo, textViewPart1, textViewPart2, textViewPart3, textViewPart4;
        Button listaFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeGruppo = itemView.findViewById(R.id.textViewNomeGruppo);
            textViewPart1 = itemView.findViewById(R.id.textViewPart1);
            textViewPart2 = itemView.findViewById(R.id.textViewPart2);
            textViewPart3 = itemView.findViewById(R.id.textViewPart3);
            textViewPart4 = itemView.findViewById(R.id.textViewPart4);
            listaFile = itemView.findViewById(R.id.buttonVediAllegati);

        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
