package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoCasoDiStudio extends AppCompatActivity {

    private ArrayList<List> list = new ArrayList<List>();
    private int position;
    TextView textViewNomeCorso, textViewEsame, textViewDescrizione, textViewProf;
    private String chiamante = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_caso_di_studio);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Dettagli caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        caricaInfocasi();
    }

    private void caricaInfocasi()
    {
        Cursor cursor = MainActivity.DB.listaCasiDiStudio(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                list.add(arrlist);
            }
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = Integer.parseInt(extras.getString("key"));
            //The key argument here must match that used in the other activity
        }
        textViewNomeCorso = findViewById(R.id.textViewNomeCaso);
        textViewNomeCorso.setText(list.get(position).get(1).toString());
        textViewEsame = findViewById(R.id.textViewEsame);
        textViewEsame.setText(list.get(position).get(3).toString());
        textViewDescrizione = findViewById(R.id.textViewDescrizione);
        textViewDescrizione.setText(list.get(position).get(2).toString());
        textViewProf = findViewById(R.id.textViewProf);
        textViewProf.setText(list.get(position).get(5).toString());
        //toastMessage(chiamante);
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
        //chiamante = item.toString();
        return super.onOptionsItemSelected(item);
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}