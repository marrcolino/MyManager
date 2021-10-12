package com.example.mymanager;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        StringBuffer buffer = new StringBuffer();
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
        }
       /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("User Entries");
        builder.setMessage(buffer.toString());
        builder.show();*/

        TextView matricola= view.findViewById(R.id.textViewMatricola);
        matricola.setText(MainActivity.utenteLoggato.matricola);
        TextView nome= view.findViewById(R.id.textViewNome);
        nome.setText(MainActivity.utenteLoggato.nome);
        TextView cognome= view.findViewById(R.id.textViewCognome);
        cognome.setText(MainActivity.utenteLoggato.cognome);
        TextView email= view.findViewById(R.id.textViewEmail);
        email.setText(MainActivity.utenteLoggato.email);
        TextView dataNascita= view.findViewById(R.id.textViewNascita);
        dataNascita.setText(MainActivity.utenteLoggato.dataNascita);
        TextView password= view.findViewById(R.id.textViewPassword);
        password.setText(MainActivity.utenteLoggato.password);

        return view;
    }
}