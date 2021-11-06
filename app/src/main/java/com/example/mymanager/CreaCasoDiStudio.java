package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +  getString(R.string.crea_cs) + "</font>"));
        // Customize the back button
        //COLOR ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0094FF")));
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
                    editTextEsame.setError(getString(R.string.fill_field));
                }
                if(descrizione.isEmpty()){
                    campiVuoti = true;
                    editTextDescrizione.setError(getString(R.string.fill_field));
                }
                if(nome.isEmpty()){
                    campiVuoti = true;
                    editTextNome.setError(getString(R.string.fill_field));
                }

                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.insertCasoDiStudio(nome, descrizione, esame, MainActivity.utenteLoggato.matricola);

                    if (insertUser) {
                        toastMessage(getString(R.string.crea_com));
                        editTextNome.setText("");
                        editTextDescrizione.setText("");
                        editTextEsame.setText("");
                    } else {
                        toastMessage(getString(R.string.some_wrong));
                    }
                } else {
                    toastMessage(getString(R.string.fill_fields));
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