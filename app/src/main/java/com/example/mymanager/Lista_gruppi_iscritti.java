package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Lista_gruppi_iscritti extends AppCompatActivity implements RecyclerGruppiIscritti.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerGruppiIscritti adapter;
    private ArrayList<List> list = new ArrayList<List>();
    private ArrayList<List> listInfo = new ArrayList<List>();
    private TextView textViewListaVuota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gruppi_iscritti);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Gruppi iscritti al caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buildListData();

        if(!list.isEmpty())
        {
            textViewListaVuota = (TextView)findViewById(R.id.textViewListaVuota);
            textViewListaVuota.setVisibility(View.GONE);
        }

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerGruppiIscritti(this, list, listInfo);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        String idCaso = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idCaso = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        Cursor cursor = MainActivity.DB.listaGruppiIscritti(idCaso);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                arrlist.add(cursor.getString(6));
                list.add(arrlist);
            }
        }
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}