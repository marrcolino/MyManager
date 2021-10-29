package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class InfoCasoDiStudio extends AppCompatActivity {

    private ArrayList<List> list = new ArrayList<List>();
    private int position;
    TextView textViewNomeCorso, textViewEsame, textViewDescrizione, textViewProf, textViewInfoNomeGruppo;
    private String chiamante = "";
    private StorageReference storageReference;
    private Button inserisci, buttonCancIscrizione, buttonIscriviti;
    private EditText editTextIscriviGruppo;
    Uri pdfUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_caso_di_studio);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> Dettagli caso di studio </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();

        textViewNomeCorso = (TextView)findViewById(R.id.textViewNomeCaso);
        textViewEsame = (TextView)findViewById(R.id.textViewEsame);
        textViewDescrizione = (TextView)findViewById(R.id.textViewDescrizione);
        textViewProf = (TextView)findViewById(R.id.textViewProf);
        textViewInfoNomeGruppo = (TextView)findViewById(R.id.textViewInfoNomeGruppo);
        editTextIscriviGruppo = (EditText)findViewById(R.id.editTextIscriviGruppo);

        inserisci = (Button)findViewById(R.id.inseriscifile);
        buttonCancIscrizione = (Button)findViewById(R.id.buttonCancIscrizione);
        buttonIscriviti = (Button)findViewById(R.id.buttonIscriviti);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String call = "";
            int index = 0;
            index = extras.getString("key").indexOf('$');
            call = extras.getString("key").substring(0, index);
            position = Integer.parseInt(extras.getString("key").substring(index+1));

            if(call.equals("cerca"))
            {
                //CHIAMATO DALLA PAGINA CercaCasiFragment
                caricaInfoCasiDaRicerca();

                buttonCancIscrizione.setVisibility(View.GONE);
                inserisci.setVisibility(View.GONE);
                //textViewInfoNomeGruppo.setVisibility(View.GONE);

                if(MainActivity.utenteLoggato.matricola.charAt(0) =='0')
                {
                    buttonIscriviti.setVisibility(View.GONE);
                    editTextIscriviGruppo.setVisibility(View.GONE);
                }
                else
                {
                    Cursor cursorNome = MainActivity.DB.checkGruppoIscritto(MainActivity.utenteLoggato.matricola, list.get(position).get(0).toString());
                    if(cursorNome.getCount()>0)
                    {
                        while (cursorNome.moveToNext()) {
                            textViewInfoNomeGruppo.setText("Nome gruppo : " + cursorNome.getString(0));
                        }
                        editTextIscriviGruppo.setEnabled(false);
                        buttonIscriviti.setEnabled(false);
                        buttonIscriviti.setText("iscritto ✓");
                    }
                }
            }
            else
            {
                //CHIAMATO DALLA PAGINA HomeFragment
                caricaInfoCasi();

                editTextIscriviGruppo.setVisibility(View.GONE);
                buttonIscriviti.setVisibility(View.GONE);
                /*if()
                {

                }*/
            }
        }

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(InfoCasoDiStudio.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                    ActivityCompat.requestPermissions(InfoCasoDiStudio.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });

        buttonCancIscrizione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ABBANDONA GRUPPO
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoCasoDiStudio.this);

                builder.setTitle("Conferma");
                builder.setMessage("Sei sicuro di voler abbandonare il caso di studio?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something and close the dialog
                        Boolean abbandonato = MainActivity.DB.abbandonaCasoDiStudio(list.get(position).get(7).toString());

                        if(abbandonato)
                        {
                            toastMessage("Caso di studio abbandonato!");
                            startActivity(new Intent(InfoCasoDiStudio.this, Home.class));
                        }
                        else {
                            toastMessage("Qualcosa è andato storto!");
                        }
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonIscriviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeGruppo = editTextIscriviGruppo.getText().toString();
                if(!nomeGruppo.equals(""))
                {
                    if(MainActivity.DB.checkNomeGruppo(nomeGruppo))
                    {
                        if(MainActivity.DB.checkCreatoreGruppo(MainActivity.utenteLoggato.matricola, nomeGruppo))
                        {
                            //ISCRIZIONE
                            AlertDialog.Builder builder = new AlertDialog.Builder(InfoCasoDiStudio.this);

                            builder.setTitle("Conferma");
                            builder.setMessage("Sei sicuro di voler iscrivere il gruppo?");

                            builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do something and close the dialog
                                    Boolean iscrizione = MainActivity.DB.updateIscrizioneGruppo(nomeGruppo, list.get(position).get(0).toString());

                                    if(iscrizione)
                                    {
                                        toastMessage("Iscrizione effettuata!");
                                        onBackPressed();

                                    }
                                    else {
                                        toastMessage("Qualcosa è andato storto!");
                                    }
                                }
                            });

                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                        else
                        {
                            toastMessage("Per iscriverti devi essere il creatore del gruppo!");
                        }
                    }
                    else
                    {
                        toastMessage("Inserire il nome di un gruppo esistente!");
                    }
                }
                else
                {
                    editTextIscriviGruppo.setError("Riempire il campo!");
                    toastMessage("Inserire il nome di un gruppo!");
                }

            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else
            Toast.makeText(InfoCasoDiStudio.this, "accetta il permesso", Toast.LENGTH_SHORT).show();
    }

    void selectPdf() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "Choose a file");
        startActivityForResult(intent,86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {

            pdfUri = data.getData();

        } else {
            Toast.makeText(InfoCasoDiStudio.this, "seleziona file", Toast.LENGTH_SHORT).show();
        }
        uploadFile(pdfUri);
    }

    private void uploadFile(Uri pdfUri) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading file");
        pd.show();


        storageReference= FirebaseStorage.getInstance().getReference();
        String fileName=pdfUri.getLastPathSegment();

            int cut = fileName.lastIndexOf('/');
            if (cut != -1) {
                fileName = fileName.substring(cut + 1);}
            cut=fileName.lastIndexOf('.');
        if (cut != -1) {
            fileName = fileName.substring(0,cut);}
        storageReference.child(list.get(position).get(7).toString()).child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"file uploaded", Snackbar.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"failed upload",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                pd.setMessage("Progress"+ (int) progressPercent + "%");
            }
        });
    }

    private void caricaInfoCasi()
    {
        Cursor cursor = MainActivity.DB.listaCasiDiStudio(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                arrlist.add(cursor.getString(6));
                arrlist.add(cursor.getString(7));
                list.add(arrlist);
            }

            textViewNomeCorso.setText("Caso di studio : " + list.get(position).get(1).toString());
            textViewEsame.setText("Nome esame : " + list.get(position).get(3).toString());
            textViewDescrizione.setText("Descrizione : " + list.get(position).get(2).toString());
            textViewProf.setText("Nome professore : " + list.get(position).get(5).toString());
            textViewInfoNomeGruppo.setText("Nome gruppo : " + list.get(position).get(7).toString());
        }
    }

    private void caricaInfoCasiDaRicerca()
    {
        Cursor cursor = MainActivity.DB.listaCasiDiStudioCerca(CercaCasiFragment.barraDiRicerca);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                arrlist.add(cursor.getString(6));
                list.add(arrlist);
            }

            textViewNomeCorso.setText("Caso di studio : " + list.get(position).get(1).toString());
            textViewEsame.setText("Nome esame : " + list.get(position).get(3).toString());
            textViewDescrizione.setText("Descrizione : " + list.get(position).get(2).toString());
            textViewProf.setText("Nome professore : " + list.get(position).get(5).toString());
        }
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
        //chiamante = item.toString();
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