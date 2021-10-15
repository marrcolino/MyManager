package com.example.mymanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static com.example.mymanager.Home.selectedImage;

public class AccountFragment extends Fragment {
    private  int STORAGE_PERMISSION_CODE = 1;
    private  int CAMERA_CODE = 1;
    Button buttonModifica;
    EditText editTextEmail, editTextPassword, editTextNome, editTextCognome, editTextDataNascita, editTextMatricola;
    ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        //TESTO SOTTOLINEATOO
        Button buttonView = (Button) view.findViewById(R.id.textViewCambiaImg);
        SpannableString content = new SpannableString( "Cambia immagine del profilo" );
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 );
        buttonView.setBackgroundColor(Color.WHITE);
        buttonView.setText(content);


        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog;
                new AlertDialog.Builder(AccountFragment.super.getContext())
                        .setTitle("Immagine mancante")
                        .setMessage("Da dove vuoi prendere l'immagine per il tuo profilo?")
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
                                if (ContextCompat.checkSelfPermission(AccountFragment.super.getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                    Home.camera = true;
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, 0);
                                }else{
                                    requestCameraPermission();
                                }

                            }
                        })
                        .setNegativeButton("Galleria", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ContextCompat.checkSelfPermission(AccountFragment.super.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                    Home.galleria = true;
                                    Toast.makeText(AccountFragment.super.getActivity(), "hai gia accettato", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent,3);
                                    dialog.dismiss();
                                }else {
                                    requestStoragePermission();
                                }

                            }
                        }).create().show();

            }
        });

        logo = view.findViewById(R.id.imageViewLogo);
        logo.setImageURI(selectedImage);

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

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AccountFragment.super.getActivity(),Manifest.permission.CAMERA)){
            new AlertDialog.Builder(AccountFragment.super.getActivity())
                    .setTitle("Permesso necessario")
                    .setMessage("Questo permesso non è ancora stato accettato")
                    .setPositiveButton("Accetta", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);

                        }
                    })
                    .setNegativeButton("Rifiuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else {
            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);
        }
    }
    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AccountFragment.super.getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(AccountFragment.super.getActivity())
                    .setTitle("Permesso necessario")
                    .setMessage("Questo permesso non è ancora stato accettato")
                    .setPositiveButton("Accetta", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Rifiuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else {
            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}