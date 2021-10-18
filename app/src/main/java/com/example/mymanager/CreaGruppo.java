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

public class CreaGruppo extends AppCompatActivity {

    Button buttonCrea;
    EditText editTextNome, editTextPart2, editTextPart3, editTextPart4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_gruppo);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Crea un caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonCrea = findViewById(R.id.buttonCreaGruppo);

        buttonCrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextNome = findViewById(R.id.editTextNomeGruppo);
                String nome = editTextNome.getText().toString();
                String partCreatore = MainActivity.utenteLoggato.matricola;
                editTextPart2 = findViewById(R.id.editTextPart2);
                String part2 = editTextPart2.getText().toString();
                editTextPart3 = findViewById(R.id.editTextPart3);
                String part3 = editTextPart3.getText().toString();
                editTextPart4 = findViewById(R.id.editTextPart4);
                String part4 = editTextPart4.getText().toString();


                boolean campiVuoti = false;

                if(nome.isEmpty()){
                    campiVuoti = true;
                    editTextNome.setError("Riempire il campo!");
                }
                if(part2.isEmpty()){
                    campiVuoti = true;
                    editTextPart2.setError("Riempire il campo!");
                }


                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.insertGruppo(nome, partCreatore, part2, part3, part4);

                    if (insertUser) {
                        toastMessage("Creazione gruppo completata!");
                        editTextNome.setText("");
                        editTextPart2.setText("");
                        editTextPart3.setText("");
                        editTextPart4.setText("");
                    } else {
                        toastMessage("Something went wrong");
                    }
                } else {
                    toastMessage("Riempire almeno i primi due campi!");
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