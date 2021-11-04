package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registrazione extends AppCompatActivity {

    Button buttonReg;
    EditText editTextEmail, editTextPassword, editTextNome, editTextCognome, editTextDataNascita, editTextMatricola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Registrazione </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //COLOR ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0094FF")));
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonReg = findViewById(R.id.buttonRegistrati);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextEmail = findViewById(R.id.editTextEmailAddress);
                String email = editTextEmail.getText().toString();
                editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();
                editTextNome = findViewById(R.id.editTextNome);
                String nome = editTextNome.getText().toString();
                editTextCognome = findViewById(R.id.editTextCognome);
                String cognome = editTextCognome.getText().toString();
                editTextDataNascita = findViewById(R.id.editTextDataNascita);
                String dataNascita = editTextDataNascita.getText().toString();
                editTextMatricola = findViewById(R.id.editTextMatricola);
                String matricola = editTextMatricola.getText().toString();

                boolean campiVuoti = false;

                if(email.isEmpty()){
                    campiVuoti = true;
                    editTextEmail.setError("Riempire il campo!");
                }
                if(password.isEmpty()){
                    campiVuoti = true;
                    editTextPassword.setError("Riempire il campo!");
                }
                if(nome.isEmpty()){
                    campiVuoti = true;
                    editTextNome.setError("Riempire il campo!");
                }
                if(cognome.isEmpty()){
                    campiVuoti = true;
                    editTextCognome.setError("Riempire il campo! ");
                }
                if(dataNascita.isEmpty()){
                    campiVuoti = true;
                    editTextDataNascita.setError("Riempire il campo! ");
                }
                if(matricola.isEmpty()){
                    campiVuoti = true;
                    editTextMatricola.setError("Riempire il campo!");
                }

                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.insertUserData( matricola , nome ,  cognome ,  email ,  password ,  dataNascita);

                    if (insertUser) {
                        toastMessage("Registrazione completata!");
                        startActivity(new Intent(Registrazione.this, Login.class));
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