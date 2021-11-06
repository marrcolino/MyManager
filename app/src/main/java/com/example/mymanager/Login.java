package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextMatricola, editTextPassword;
    TextView recupero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        recupero = findViewById(R.id.textRecupero);
        //TESTO SOTTOLINEATO
        TextView textView = findViewById(R.id.textViewRegistrati);
        SpannableString content = new SpannableString( getString(R.string.not_have_account) );
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 );
        textView.setText(content);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Login </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //COLOR ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0094FF")));
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.blue_app));

        buttonLogin = findViewById(R.id.buttonLogin);
        recupero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recupero.setText(getString(R.string.scrivi_email));
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextMatricola = findViewById(R.id.editTextMatricola);
                String matricola = editTextMatricola.getText().toString();
                editTextPassword = findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();

                boolean campiVuoti = false;

                if(matricola.isEmpty()){
                    campiVuoti = true;
                    editTextMatricola.setError(getString(R.string.fill_field));
                }
                if(password.isEmpty()){
                    campiVuoti = true;
                    editTextPassword.setError(getString(R.string.fill_field));
                }

                if (!campiVuoti) {
                    Cursor cursor = MainActivity.DB.login(matricola,password);
                    if(cursor.getCount()>0){
                        if(cursor.getCount()==0){
                            toastMessage(getString(R.string.error_list));

                        }else {
                            while(cursor.moveToNext()) {

                            MainActivity.utenteLoggato.matricola=cursor.getString(0);
                            MainActivity.utenteLoggato.nome=cursor.getString(1);
                            MainActivity.utenteLoggato.cognome=cursor.getString(2);
                            MainActivity.utenteLoggato.email=cursor.getString(3);
                            MainActivity.utenteLoggato.password=cursor.getString(4);
                            MainActivity.utenteLoggato.dataNascita=cursor.getString(5);
                            }
                        }
                        toastMessage(getString(R.string.login_success));
                        startActivity(new Intent(Login.this, Home.class));
                    }else{
                        toastMessage(getString(R.string.incorrect_fresh_pass));
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