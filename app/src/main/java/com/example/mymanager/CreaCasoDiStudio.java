package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreaCasoDiStudio extends AppCompatActivity {

    Button buttonCrea;
    EditText editTextNome, editTextEsame, editTextDescrizione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_caso_di_studio);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Crea un caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonCrea = findViewById(R.id.buttonCrea);

        buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextEsame = findViewById(R.id.editTextEsame);
                String esame = editTextEsame.getText().toString();
                editTextDescrizione = findViewById(R.id.editTextDescrizione);
                String descrizione = editTextDescrizione.getText().toString();
                editTextNome = findViewById(R.id.editTextNomeCaso);
                String nome = editTextNome.getText().toString();

                boolean campiVuoti = false;

                if(esame.isEmpty()){
                    campiVuoti = true;
                    editTextEsame.setError("Riempire il campo!");
                }
                if(descrizione.isEmpty()){
                    campiVuoti = true;
                    editTextDescrizione.setError("Riempire il campo!");
                }
                if(nome.isEmpty()){
                    campiVuoti = true;
                    editTextNome.setError("Riempire il campo!");
                }

                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.insertCasoDiStudio(nome, descrizione, esame, MainActivity.utenteLoggato.matricola);

                    if (insertUser) {
                        toastMessage("Creazione completata!");
                        editTextNome.setText("");
                        editTextDescrizione.setText("");
                        editTextEsame.setText("");
                    } else {
                        toastMessage("Something went wrong");
                    }
                } else {
                    toastMessage("Riempire tutti i campi!");
                }
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