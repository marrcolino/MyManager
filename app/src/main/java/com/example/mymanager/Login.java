package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Login extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TESTO SOTTOLINEATO
        TextView textView = findViewById(R.id.textViewRegistrati);
        SpannableString content = new SpannableString( "Non possiedi un account? Registrati" );
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 );
        textView.setText(content);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\"> Login </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextEmail = findViewById(R.id.editTextEmailAddress);
                String email = editTextEmail.getText().toString();
                editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextEmail.getText().toString();

                boolean campiVuoti = false;

                if(email.isEmpty()){
                    campiVuoti = true;
                    editTextEmail.setError("Riempire il campo!");
                }
                if(password.isEmpty()){
                    campiVuoti = true;
                    editTextPassword.setError("Riempire il campo!");
                }

                if (!campiVuoti) {
                    toastMessage("Registrazione completata!");
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

    //TAG REGISTRATI CLICK
    public void onClick(View v) {
        startActivity(new Intent(Login.this, Registrazione.class));
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}