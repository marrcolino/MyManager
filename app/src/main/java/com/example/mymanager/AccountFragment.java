package com.example.mymanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    Button buttonModifica;
    EditText editTextEmail, editTextPassword, editTextNome, editTextCognome, editTextDataNascita, editTextMatricola;
    ImageView imageViewLogo;
    Drawable drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //TESTO SOTTOLINEATO
        Button buttonView = (Button) view.findViewById(R.id.textViewCambiaImg);
        SpannableString content = new SpannableString( "Cambia immagine del profilo" );
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 );
        buttonView.setBackgroundColor(Color.WHITE);
        buttonView.setText(content);

        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });


        imageViewLogo  = (ImageView)view.findViewById(R.id.imageViewLogo);
        imageViewLogo.setImageDrawable(drawable);

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

        buttonModifica = (Button)view.findViewById(R.id.buttonModifica);

        buttonModifica.setOnClickListener(new View.OnClickListener() {
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
                    startActivity(new Intent(getActivity(), Home.class));
                } else {
                    toastMessage("Riempire tutti i campi!");
                }

            }
        });

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