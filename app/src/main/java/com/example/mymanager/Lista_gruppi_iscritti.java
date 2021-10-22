package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista_gruppi_iscritti extends AppCompatActivity implements RecyclerGruppiIscritti.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerGruppiIscritti adapter;
    private ArrayList<List> list = new ArrayList<List>();
    private ArrayList<List> listInfo = new ArrayList<List>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gruppi_iscritti);

        buildListData();

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerGruppiIscritti(this, list, listInfo);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        //LISTA CASI DI STUDIO DELL'UTENTE LOGGATO
        /*Cursor cursor = MainActivity.DB.listaCasiProf(MainActivity.utenteLoggato.matricola);
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
        }*/
        String idCaso = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idCaso = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        Cursor cursor = MainActivity.DB.listaGruppiIscritti(idCaso);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Cursor cursorInfo = null;
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                arrlist.add(cursor.getString(6));
                list.add(arrlist);

                /*for(int i = 2; i<6; i++)
                {
                    cursorInfo = null;
                    cursorInfo = MainActivity.DB.listaInfoStudentiGruppiIscritti(cursor.getString(i));
                    if (cursorInfo.getCount() > 0) {
                        while (cursorInfo.moveToNext()) {
                            List<String> arrlistInfo = new ArrayList<String>();
                            arrlistInfo.add(cursorInfo.getString(0));
                            arrlistInfo.add(cursorInfo.getString(1));
                            arrlistInfo.add(cursorInfo.getString(2));
                            listInfo.add(arrlistInfo);
                        }
                    }

                }*/

            }
        }
        /*
        toastMessage(listInfo.get(0).get(0) + " " + listInfo.get(0).get(1) + " " + listInfo.get(0).get(2));
        toastMessage(listInfo.get(1).get(0) + " " + listInfo.get(1).get(1) + " " + listInfo.get(1).get(2));
        toastMessage(listInfo.get(2).get(0) + " " + listInfo.get(2).get(1) + " " + listInfo.get(2).get(2));
        toastMessage(listInfo.get(3).get(0) + " " + listInfo.get(3).get(1) + " " + listInfo.get(3).get(2));*/
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}