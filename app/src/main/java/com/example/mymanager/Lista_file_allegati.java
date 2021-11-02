package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Lista_file_allegati extends AppCompatActivity implements RecyclerFile.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerFile adapter;
    StorageReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Button download;
    public static Boolean galleria = false;
    private ArrayList<List> list = new ArrayList<List>();
    private String nomeCartella = "";
    TextView textViewListaVuotaFileAllegati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_file_allegati);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#0094FF\">" + getString(R.string.app_name) + "</font>"
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\"> File allegati </font>"));
        // Customize the back button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        download = findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nomeCartella = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        recyclerView = findViewById(R.id.recyclerviewFile);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buildListData();

        if(!list.isEmpty())
        {
            textViewListaVuotaFileAllegati = (TextView)findViewById(R.id.textViewListaVuotaFileAllegati);
            textViewListaVuotaFileAllegati.setVisibility(View.GONE);
        }
    }

    private void buildListData() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child(nomeCartella+"/");
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            List<String> arrlist = new ArrayList<String>();
                            arrlist.add(item.getName());
                            arrlist.add(nomeCartella);
                            list.add(arrlist);
                        }
                        adapter = new RecyclerFile(Lista_file_allegati.this, list);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
    }

    private void download() {
        ProgressDialog pd = new ProgressDialog(Lista_file_allegati.this);
        pd.setTitle("download file");
        pd.setMessage("Downloading Please Wait!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("test10/1006-3040-2-PB");
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String url = uri.toString();
            downloadFile(Lista_file_allegati.this, "1006-3040-2-PB", "pdf", DIRECTORY_DOWNLOADS, url);

            Toast.makeText(Lista_file_allegati.this, "Download Completed", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }).addOnFailureListener(e -> {

            Toast.makeText(Lista_file_allegati.this, "Download Failed", Toast.LENGTH_LONG).show();
            pd.dismiss();

        });
    }

    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(List casi, int position) {

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}