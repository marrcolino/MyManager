package com.example.mymanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

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
        SpannableString content = new SpannableString( getString(R.string.change_picture) );
        content.setSpan( new UnderlineSpan() , 0 , content.length() , 0 );
        buttonView.setBackgroundColor(Color.WHITE);
        buttonView.setText(content);

        buttonView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog;
                new AlertDialog.Builder(AccountFragment.super.getContext())
                        .setTitle(getString(R.string.miss_image))
                        .setMessage(getString(R.string.where_pic))
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
                        .setNegativeButton( getString(R.string.gallery), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ContextCompat.checkSelfPermission(AccountFragment.super.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                                    Home.galleria = true;
                                    Toast.makeText(AccountFragment.super.getActivity(), getString(R.string.already_accept), Toast.LENGTH_LONG).show();
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
        Home.profile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(logo);
            }
        });

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
                    editTextEmail.setError(getString(R.string.fill_field));
                }
                if(password.isEmpty()){
                    campiVuoti = true;
                    editTextPassword.setError(getString(R.string.fill_field));
                }
                if(nome.isEmpty()){
                    campiVuoti = true;
                    editTextNome.setError(getString(R.string.fill_field));
                }
                if(cognome.isEmpty()){
                    campiVuoti = true;
                    editTextCognome.setError(getString(R.string.fill_field));
                }
                if(dataNascita.isEmpty()){
                    campiVuoti = true;
                    editTextDataNascita.setError(getString(R.string.fill_field));
                }
                if(matricola.isEmpty()){
                    campiVuoti = true;
                    editTextMatricola.setError(getString(R.string.fill_field));
                }

                if (!campiVuoti) {

                    boolean insertUser = MainActivity.DB.updateUserData( matricola , nome ,  cognome ,  email ,  password ,  dataNascita);

                    if (insertUser) {
                        toastMessage(getString(R.string.change_complet));
                    } else {
                        toastMessage(getString(R.string.some_wrong));
                    }
                    startActivity(new Intent(getActivity(), Home.class));
                } else {
                    toastMessage(getString(R.string.fill_fields));
                }

            }
        });

        return view;
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(AccountFragment.super.getActivity(),Manifest.permission.CAMERA)){
            new AlertDialog.Builder(AccountFragment.super.getActivity())
                    .setTitle(getString(R.string.perm_requ))
                    .setMessage(getString(R.string.perm_not_yet))
                    .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_CODE);

                        }
                    })
                    .setNegativeButton(getString(R.string.ref), new DialogInterface.OnClickListener() {
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
                    .setTitle(getString(R.string.perm_requ))
                    .setMessage(getString(R.string.perm_not_yet))
                    .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            ActivityCompat.requestPermissions(AccountFragment.super.getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton(getString(R.string.ref), new DialogInterface.OnClickListener() {
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