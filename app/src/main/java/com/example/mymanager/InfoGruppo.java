package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoGruppo extends AppCompatActivity {

    TextView nomeGruppo;
    private ArrayList<List> list = new ArrayList<List>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gruppo);

        Cursor cursor = MainActivity.DB.listaGruppi(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
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
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            position = Integer.parseInt(extras1.getString("key2"));
            //The key argument here must match that used in the other activity
        }
        nomeGruppo = (TextView)findViewById(R.id.nomeGruppo);
        nomeGruppo.setText(list.get(position).get(1).toString());
    }

}