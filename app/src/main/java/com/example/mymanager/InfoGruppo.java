package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    private Button salvaMod, abbandona, buttonCancellaGruppo;
    private TextView nomeCaso, textViewCreatore, textViewModPart2, textViewModPart3, textViewModPart4;
    private EditText nomeGruppo, part2, part3, part4, editTextCreatore;
    private ArrayList<List> list = new ArrayList<List>();
    private ArrayList<List> listInfo = new ArrayList<List>();
    private int position;
    private String partecipante2, partecipante3, partecipante4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gruppo);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +  getString(R.string.info_g) + "</font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //COLOR ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0094FF")));
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
                //arrlist.add(cursor.getString(7));
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
        buttonCancellaGruppo = (Button)findViewById(R.id.buttonCancellaGruppo);

        nomeCaso = (TextView) findViewById(R.id.textViewNomeCasoGruppo);
        Cursor cursorNomeCaso = MainActivity.DB.nomeCaso(list.get(position).get(6).toString());
        if(cursorNomeCaso.getCount() > 0)
        {
            while (cursorNomeCaso.moveToNext()) {
                nomeCaso.setText(getString(R.string.case_study) + cursorNomeCaso.getString(0));
            }
        }

        nomeGruppo = (EditText) findViewById(R.id.editTextModNomeGruppo);
        nomeGruppo.setText(list.get(position).get(1).toString());

        for(int i = 2; i<6; i++){
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

        textViewCreatore = (TextView) findViewById(R.id.textViewCreatore);
        textViewCreatore.setText(getString(R.string.creator) +listInfo.get(0).get(0).toString() + " " + listInfo.get(0).get(1).toString());
        editTextCreatore = (EditText) findViewById(R.id.editTextCreatore);
        editTextCreatore.setText(list.get(position).get(2).toString());

        textViewModPart2 = (TextView) findViewById(R.id.textViewModPart2);
        textViewModPart2.setText(listInfo.get(1).get(0).toString() + " " + listInfo.get(1).get(1).toString());
        part2 = (EditText) findViewById(R.id.editTextModPart2);
        part2.setText(list.get(position).get(3).toString());

        textViewModPart3 = (TextView) findViewById(R.id.textViewModPart3);
        textViewModPart3.setText(listInfo.get(2).get(0).toString() + " " + listInfo.get(2).get(1).toString());
        part3 = (EditText) findViewById(R.id.editTextModPart3);
        part3.setText(list.get(position).get(4).toString());

        textViewModPart4 = (TextView) findViewById(R.id.textViewModPart4);
        textViewModPart4.setText(listInfo.get(3).get(0).toString() + " " + listInfo.get(3).get(1).toString());
        part4 = (EditText) findViewById(R.id.editTextModPart4);
        part4.setText(list.get(position).get(5).toString());

        if(!MainActivity.utenteLoggato.matricola.equals(list.get(position).get(2).toString()))
        {
            //PARTECIPANTE
            nomeGruppo.setEnabled(false);
            part2.setEnabled(false);
            part3.setEnabled(false);
            part4.setEnabled(false);
            salvaMod.setVisibility(View.GONE);
            buttonCancellaGruppo.setVisibility(View.GONE);
        }
        else
        {
            //CREATORE
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
                        part2.setError(getString(R.string.noex_fresh));
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
                        part3.setError(getString(R.string.noex_fresh));
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
                        part4.setError(getString(R.string.noex_fresh));
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

                    if(updateGruppo)
                        toastMessage(getString(R.string.saved_change));
                    else
                        toastMessage(getString(R.string.some_wrong));
                }
                else
                {
                    toastMessage(getString(R.string.one_freshman_same_incorr));
                }
            }
        });

        abbandona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoGruppo.this);

                builder.setTitle(getString(R.string.conf));
                builder.setMessage(getString(R.string.are_you_sure_leave_group));

                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Boolean gruppoAbbandonato = false;
                        if(MainActivity.utenteLoggato.matricola.equals(part2.getText().toString()))
                        {
                            gruppoAbbandonato = MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante2");
                        }
                        else if(MainActivity.utenteLoggato.matricola.equals(part3.getText().toString()))
                        {
                            gruppoAbbandonato = MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante3");
                        }
                        else
                        {
                            gruppoAbbandonato = MainActivity.DB.abbandonaGruppo(list.get(position).get(0).toString(), "matricolaPartecipante4");
                        }

                        if(gruppoAbbandonato)
                        {
                            toastMessage(getString(R.string.group_leaved));
                            dialog.dismiss();
                            startActivity(new Intent(InfoGruppo.this, Home.class));
                        }
                        else
                        {
                            toastMessage(getString(R.string.some_wrong));
                        }
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

        buttonCancellaGruppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoGruppo.this);

                builder.setTitle(getString(R.string.conf));
                builder.setMessage(getString(R.string.sure_delete_group));

                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Boolean gruppoCancellato = false;
                        gruppoCancellato = MainActivity.DB.deleteGruppo(list.get(position).get(0).toString());

                        if(gruppoCancellato)
                        {
                            toastMessage(getString(R.string.group_delet));
                            dialog.dismiss();
                            startActivity(new Intent(InfoGruppo.this, Home.class));
                        }
                        else
                        {
                            toastMessage(getString(R.string.some_wrong));
                        }
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