package com.example.mymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    Button buttonModifica;
    EditText editTextEmail, editTextPassword, editTextNome, editTextCognome, editTextDataNascita, editTextMatricola;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        /*StringBuffer buffer = new StringBuffer();
        Cursor res = MainActivity.DB.getData(MainActivity.utenteLoggato.matricola);
        if(res.getCount()==0){
            Toast.makeText(getActivity(),"No Entry Exists", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Home.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

        }else {
            while (res.moveToNext()) {
                buffer.append("matricola :" + res.getString(0) + "\n");
                buffer.append("nome :" + res.getString(1) + "\n");
                buffer.append("cognome :" + res.getString(2) + "\n\n");
                buffer.append("email :" + res.getString(3) + "\n");
                buffer.append("password :" + res.getString(4) + "\n");
                buffer.append("dataNascita :" + res.getString(5) + "\n\n");
            }
        }*/
       /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("User Entries");
        builder.setMessage(buffer.toString());
        builder.show();*/

        editTextEmail = (EditText)view.findViewById(R.id.editTextEmail);
        editTextEmail.setText(MainActivity.utenteLoggato.email);

        editTextPassword = (EditText)view.findViewById(R.id.editTextPassword);
        editTextPassword.setText(MainActivity.utenteLoggato.password);

        editTextNome = (EditText)view.findViewById(R.id.editTextNome);
        editTextNome.setText(MainActivity.utenteLoggato.nome);

        editTextCognome = (EditText)view.findViewById(R.id.editTextCognome);
        editTextCognome.setText(MainActivity.utenteLoggato.cognome);

        editTextDataNascita = (EditText)view.findViewById(R.id.editTextNascita);
        editTextDataNascita.setText(MainActivity.utenteLoggato.dataNascita);

        editTextMatricola = (EditText)view.findViewById(R.id.editTextMatricola);
        editTextMatricola.setText(MainActivity.utenteLoggato.matricola);

        buttonModifica = (Button)view.findViewById(R.id.buttonLogin);

        /*buttonModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String nome = editTextNome.getText().toString();
                String cognome = editTextCognome.getText().toString();
                String dataNascita = editTextDataNascita.getText().toString();
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
                    editTextCognome.setError("Riempire il campo!");
                }
                if(dataNascita.isEmpty()){
                    campiVuoti = true;
                    editTextDataNascita.setError("Riempire il campo!");
                }
                if(matricola.isEmpty()){
                    campiVuoti = true;
                    editTextMatricola.setError("Riempire il campo!");
                }

                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.updateUserData( matricola , nome ,  cognome ,  email ,  password ,  dataNascita);

                    if (insertUser) {
                        toastMessage("Modifica completata!");
                    } else {
                        toastMessage("Something went wrong");
                    }
                } else {
                    toastMessage("Riempire tutti i campi!");
                }

            }
        });*/

        return view;
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}