package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoCasoDiStudio extends AppCompatActivity {

    TextView text;
    private ArrayList<List> list = new ArrayList<List>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_caso_di_studio);


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
        text = (TextView)findViewById(R.id.textView1);
        text.setText(list.get(position).get(1).toString());
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}