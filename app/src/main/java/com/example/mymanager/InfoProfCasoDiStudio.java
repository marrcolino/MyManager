package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class InfoProfCasoDiStudio extends AppCompatActivity {

    private ArrayList<List> list = new ArrayList<List>();
    private int position;
    private EditText editTextModNomeCaso, editTextModNomeEsame, editTextModDescrizione;
    private Button buttonSalvaModifiche, buttonEliminaCasoDiStudio, buttonListaGruppi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_prof_caso_di_studio);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Dettagli caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        caricaInfocasi();

        buttonSalvaModifiche = findViewById(R.id.buttonSalvaModifiche);
        buttonEliminaCasoDiStudio = findViewById(R.id.buttonEliminaCasoDiStudio);
        buttonListaGruppi = findViewById(R.id.buttonListaGruppi);

        buttonListaGruppi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentManager fragmentManager =  getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment map = new Gruppi_iscrittiFragment();
                fragmentTransaction.add(R.id.nav_host_fragment, map);
                fragmentTransaction.commit();

               /* Bundle bundle = new Bundle();
                bundle.putString("key", "prova");
                // set Fragmentclass Arguments
                Gruppi_iscrittiFragment fragobj = new Gruppi_iscrittiFragment();
                fragobj.setArguments(bundle);*/

                startActivity(new Intent(InfoProfCasoDiStudio.this ,Lista_gruppi_iscritti.class));
            }
        });

        buttonSalvaModifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaModifiche();
                startActivity(new Intent(InfoProfCasoDiStudio.this, Home.class));
            }
        });

        buttonEliminaCasoDiStudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoProfCasoDiStudio.this);

                builder.setTitle("Conferma");
                builder.setMessage("Sei sicuro di voler eliminare il caso di studio?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        eliminaCasoDiStudio();
                        dialog.dismiss();
                        startActivity(new Intent(InfoProfCasoDiStudio.this, Home.class));
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

    private void salvaModifiche()
    {
        editTextModNomeEsame = findViewById(R.id.editTextModNomeEsame);
        String esame = editTextModNomeEsame.getText().toString();
        editTextModDescrizione = findViewById(R.id.editTextModDescrizione);
        String descrizione = editTextModDescrizione.getText().toString();
        editTextModNomeCaso = findViewById(R.id.editTextModNomeCaso);
        String nome = editTextModNomeCaso.getText().toString();

        boolean campiVuoti = false;

        if(esame.isEmpty()){
            campiVuoti = true;
            editTextModNomeEsame.setError("Riempire il campo!");
        }
        if(descrizione.isEmpty()){
            campiVuoti = true;
            editTextModDescrizione.setError("Riempire il campo!");
        }
        if(nome.isEmpty()){
            campiVuoti = true;
            editTextModNomeCaso.setError("Riempire il campo!");
        }

        if (!campiVuoti) {
            boolean updateInfo = MainActivity.DB.updateInfoProfCasiDiStudio(list.get(position).get(0).toString(), nome, descrizione, esame);

            if (updateInfo) {
                toastMessage("Modifica completata!");
            } else {
                toastMessage("Something went wrong");
            }
        } else {
            toastMessage("Riempire tutti i campi!");
        }
    }

    private void eliminaCasoDiStudio()
    {
        boolean deleteCaso = MainActivity.DB.deleteCasoDiStudio(list.get(position).get(0).toString());

        if (deleteCaso) {
            toastMessage("Caso di studio eliminato!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void caricaInfocasi()
    {
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = Integer.parseInt(extras.getString("key"));
            //The key argument here must match that used in the other activity
        }
        editTextModNomeCaso = findViewById(R.id.editTextModNomeCaso);
        editTextModNomeCaso.setText(list.get(position).get(1).toString());
        editTextModNomeEsame = findViewById(R.id.editTextModNomeEsame);
        editTextModNomeEsame.setText(list.get(position).get(3).toString());
        editTextModDescrizione = findViewById(R.id.editTextModDescrizione);
        editTextModDescrizione.setText(list.get(position).get(2).toString());
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