package com.example.mymanager;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row_file_allegati,parent,false);
        return new RecyclerFile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*holder.mName.setText(list.get(position).toString());
        holder.mLink.setText(list.get(position).toString());
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(holder.mName.getContext(),list.get(position).toString(),".pdf",DIRECTORY_DOWNLOADS,list.get(position).toString());
            }
        });*/
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
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName;TextView mLink;
        Button mDownload;
        public ViewHolder(@NonNull View view) {
            super(view);
            mName=itemView.findViewById(R.id.name);
            mLink=itemView.findViewById(R.id.link);
            mDownload=itemView.findViewById(R.id.down);
        }
    }
}
