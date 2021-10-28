package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoGruppo extends AppCompatActivity {

    private Button salvaMod, abbandona;
    private TextView nomeCaso;
    private EditText nomeGruppo, part2, part3, part4;
    private ArrayList<List> list = new ArrayList<List>();
    private int position;
    private String partecipante2, partecipante3, partecipante4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gruppo);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Info gruppo </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                arrlist.add(cursor.getString(7));
                list.add(arrlist);
            }
        }
        Bundle extras1 = getIntent().getExtras();
        if (extras1 != null) {
            position = Integer.parseInt(extras1.getString("key2"));
            //The key argument here must match that used in the other activity
        }

        salvaMod = (Button)findViewById(R.id.buttonSalvaModGruppo);
        abbandona = (Button)findViewById(R.id.buttonAbbandonaGruppo);

        nomeCaso = (TextView) findViewById(R.id.textViewNomeCasoGruppo);
        nomeCaso.setText(list.get(position).get(7).toString());

        nomeGruppo = (EditText) findViewById(R.id.editTextModNomeGruppo);
        nomeGruppo.setText(list.get(position).get(1).toString());

        part2 = (EditText) findViewById(R.id.editTextModPart2);
        part2.setText(list.get(position).get(3).toString());

        part3 = (EditText) findViewById(R.id.editTextModPart3);
        part3.setText(list.get(position).get(4).toString());

        part4 = (EditText) findViewById(R.id.editTextModPart4);
        part4.setText(list.get(position).get(5).toString());

        if(!MainActivity.utenteLoggato.matricola.equals(list.get(position).get(2).toString()))
        {
            nomeGruppo.setEnabled(false);
            part2.setEnabled(false);
            part3.setEnabled(false);
            part4.setEnabled(false);
            salvaMod.setVisibility(View.GONE);
        }
        else
        {
            abbandona.setVisibility(View.GONE);
        }

        salvaMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean matricoleGiuste = true;

                if(part2.getText().toString().equals(""))
                {
                    partecipante2 = "vuoto";
                }
                else
                {
                    if(MainActivity.DB.checkIDStudente(part2.getText().toString()))
                    {
                        partecipante2 = part2.getText().toString();
                    }
                    else{
                        part2.setError("Matricola insesistente!");
                        matricoleGiuste = false;
                    }
                }

                if(part3.getText().toString().equals(""))
                {
                    partecipante3 = "vuoto";
                }
                else
                {
                    if(MainActivity.DB.checkIDStudente(part3.getText().toString())) {
                        partecipante3 = part3.getText().toString();
                    }
                    else{
                        part3.setError("Matricola insesistente!");
                        matricoleGiuste = false;
                    }
                }

                if(part4.getText().toString().equals(""))
                {
                    partecipante4 = "vuoto";
                }
                else
                {
                    if(MainActivity.DB.checkIDStudente(part4.getText().toString())) {
                        partecipante4 = part4.getText().toString();
                    }
                    else {
                        part4.setError("Matricola insesistente!");
                        matricoleGiuste = false;
                    }
                }

                if(matricoleGiuste
                        && (!partecipante2.equals("vuoto") || !partecipante3.equals("vuoto") || !partecipante4.equals("vuoto"))
                        && (!partecipante2.equals(partecipante3) && !partecipante2.equals(partecipante4) && !partecipante3.equals(partecipante4)))
                {
                    Boolean updateGruppo;
                    if(partecipante2.equals("vuoto"))
                    {
                        if(!partecipante3.equals("vuoto"))
                        {
                            updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante3, partecipante2, partecipante4);
                        }
                        else
                        {
                            updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante4, partecipante2, partecipante3);
                        }
                    }
                    else
                    {
                        updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante2, partecipante3, partecipante4);
                    }
                    toastMessage("Modifiche salvate!");
                }
                else
                {
                    toastMessage("Una delle matricole è errata o uguale alle altre, oppure inserire almeno un partecipante!");
                }
            }
        });

        abbandona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoGruppo.this);

                builder.setTitle("Conferma");
                builder.setMessage("Sei sicuro di voler abbandonare il gruppo?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if(MainActivity.utenteLoggato.matricola.equals(part2.getText().toString()))
                        {
                            MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante2");
                        }
                        else if(MainActivity.utenteLoggato.matricola.equals(part3.getText().toString()))
                        {
                            MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante3");
                        }
                        else
                        {
                            MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante4");
                        }
                        toastMessage("Gruppo abbandonato!");
                        dialog.dismiss();
                        startActivity(new Intent(InfoGruppo.this, Home.class));
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}