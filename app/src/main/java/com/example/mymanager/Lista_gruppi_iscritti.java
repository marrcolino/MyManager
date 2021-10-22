package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Lista_gruppi_iscritti extends AppCompatActivity implements RecyclerGruppiIscritti.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerGruppiIscritti adapter;
    private ArrayList<List> list = new ArrayList<List>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gruppi_iscritti);

        buildListData();

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerGruppiIscritti(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        //LISTA CASI DI STUDIO DELL'UTENTE LOGGATO
        Cursor cursor = MainActivity.DB.listaCasiProf(MainActivity.utenteLoggato.matricola);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                list.add(arrlist);
            }
        }
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}