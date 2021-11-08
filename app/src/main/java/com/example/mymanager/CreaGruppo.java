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

public class CreaGruppo extends AppCompatActivity {

    Button buttonCrea;
    EditText editTextNome, editTextPart2, editTextPart3, editTextPart4;
    private String creatore, partecipante2, partecipante3, partecipante4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_gruppo);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.crea_grup) + "</font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //COLOR ACTION BAR
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0094FF")));
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
                    editTextNome.setError(getString(R.string.fill_field));
                }
                if(part2.isEmpty()){
                    campiVuoti = true;
                    editTextPart2.setError(getString(R.string.fill_field));
                }


                if (!campiVuoti) {

                    //toastMessage(part3 + " " + part4);
                    if(part3.isEmpty())
                    {
                        part3 = "vuoto";
                    }
                    if(part4.isEmpty())
                    {
                        part4 = "vuoto";
                    }

                    boolean matricoleGiuste = true;
                    creatore = partCreatore;

                    if(editTextPart2.getText().toString().equals(""))
                    {
                        partecipante2 = "vuoto";
                    }
                    else
                    {
                        if(MainActivity.DB.checkIDStudente(editTextPart2.getText().toString()))
                        {
                            partecipante2 = editTextPart2.getText().toString();
                        }
                        else{
                            editTextPart2.setError(getString(R.string.noex_fresh));
                            matricoleGiuste = false;
                        }
                    }

                    if(editTextPart3.getText().toString().equals(""))
                    {
                        partecipante3 = "vuoto";
                    }
                    else
                    {
                        if(MainActivity.DB.checkIDStudente(editTextPart3.getText().toString())) {
                            partecipante3 = editTextPart3.getText().toString();
                        }
                        else{
                            editTextPart3.setError(getString(R.string.noex_fresh));
                            matricoleGiuste = false;
                        }
                    }

                    if(editTextPart4.getText().toString().equals(""))
                    {
                        partecipante4 = "vuoto";
                    }
                    else
                    {
                        if(MainActivity.DB.checkIDStudente(editTextPart4.getText().toString())) {
                            partecipante4 = editTextPart4.getText().toString();
                        }
                        else {
                            editTextPart4.setError(getString(R.string.noex_fresh));
                            matricoleGiuste = false;
                        }
                    }

                    if(matricoleGiuste
                            && (!partecipante2.equals("vuoto") || !partecipante3.equals("vuoto") || !partecipante4.equals("vuoto")))
                    //&& (!partecipante2.equals(partecipante3) && !partecipante2.equals(partecipante4) && !partecipante3.equals(partecipante4)))
                    {
                        if(partecipante2.equals("vuoto"))
                        {
                            partecipante2 = "vuoto2";
                        }
                        if(partecipante3.equals("vuoto"))
                        {
                            partecipante3 = "vuoto3";
                        }
                        if(partecipante4.equals("vuoto"))
                        {
                            partecipante4 = "vuoto4";
                        }

                        if(!creatore.equals(partecipante2) && !creatore.equals(partecipante3) && !creatore.equals(partecipante4)
                                && !partecipante2.equals(partecipante3) && !partecipante2.equals(partecipante4) && !partecipante3.equals(partecipante4))
                        {
                            if(partecipante2.equals("vuoto2"))
                            {
                                partecipante2 = "vuoto";
                            }
                            if(partecipante3.equals("vuoto3"))
                            {
                                partecipante3 = "vuoto";
                            }
                            if(partecipante4.equals("vuoto4"))
                            {
                                partecipante4 = "vuoto";
                            }

                            if(!MainActivity.DB.checkNomeGruppo(nome))
                            {

                                Boolean insertUser;
                                if(partecipante2.equals("vuoto"))
                                {
                                    if(!partecipante3.equals("vuoto"))
                                    {
                                        insertUser = MainActivity.DB.insertGruppo(nome, partCreatore, part3, part2, part4);
                                        //updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante3, partecipante2, partecipante4);
                                    }
                                    else
                                    {
                                        insertUser = MainActivity.DB.insertGruppo(nome, partCreatore, part4, part2, part3);
                                        //updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante4, partecipante2, partecipante3);
                                    }
                                }
                                else
                                {
                                    insertUser = MainActivity.DB.insertGruppo(nome, partCreatore, part2, part3, part4);
                                    //updateGruppo = MainActivity.DB.updateGruppo(list.get(position).get(0).toString(), nomeGruppo.getText().toString(), partecipante2, partecipante3, partecipante4);
                                }

                                if(insertUser)
                                {
                                    toastMessage(getString(R.string.group_cre_com));
                                    editTextNome.setText("");
                                    editTextPart2.setText("");
                                    editTextPart3.setText("");
                                    editTextPart4.setText("");
                                }
                                else
                                {
                                    toastMessage(getString(R.string.some_wrong));
                                }
                            }
                            else
                            {
                                toastMessage(getString(R.string.grop_name_alre));
                            }
                        }
                        else
                        {
                            toastMessage(getString(R.string.som_fre_same));
                        }
                    }
                    else
                    {
                        toastMessage(getString(R.string.one_fre_incorr));
                    }
                } else {
                    toastMessage(getString(R.string.fill_two_fiel));
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