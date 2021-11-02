package com.example.mymanager;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class RecyclerFile extends RecyclerView.Adapter<RecyclerFile.ViewHolder> {

    private List<List> list;
    private LayoutInflater mInflater;

    public RecyclerFile(Context context , ArrayList<List> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_file_allegati,parent,false);
        return new RecyclerFile.ViewHolder(view);*/
        View view = mInflater.inflate(R.layout.my_row_file_allegati, parent, false);
        return new RecyclerFile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nomeFile = list.get(position).get(0).toString();
        String nomeCartella = list.get(position).get(1).toString();
        holder.mName.setText(nomeFile);
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(nomeCartella+ "/" + nomeFile);
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String url = uri.toString();
                    String formato = "";//url.substring(url.lastIndexOf('.')-1);
                    //String formato = url.substring(url.lastIndexOf('.')-1);
                    //toastMessage(holder.mName.getContext(), url);
                    downloadFile(holder.mName.getContext(), nomeFile, formato, DIRECTORY_DOWNLOADS, url);
                }).addOnFailureListener(e -> {
                });

            }
        });
    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(List casi, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mName;//TextView mLink;
        Button mDownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.TextViewNomeFile);
            //mLink=itemView.findViewById(R.id.link);
            mDownload=itemView.findViewById(R.id.buttonScarica);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void toastMessage(Context context, String testo){
        Toast.makeText(context, testo, Toast.LENGTH_SHORT).show();
    }
}
