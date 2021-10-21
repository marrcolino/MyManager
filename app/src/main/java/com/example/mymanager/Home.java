package com.example.mymanager;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Home extends AppCompatActivity {

    public static StorageReference profile;
    private StorageReference storageReference;
    private AppBarConfiguration mAppBarConfiguration;
    public static Boolean camera = false;
    public static Boolean galleria = false;
    public static Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_crea_gruppi, R.id.nav_groups, R.id.nav_casiDiStudio, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        final TextView Name_nav = (TextView) headerView.findViewById(R.id.textViewNomeCognome);
        final TextView Email_nav = (TextView) headerView.findViewById(R.id.textViewEmail);
        Name_nav.setText(MainActivity.utenteLoggato.nome + " " + MainActivity.utenteLoggato.cognome);
        Email_nav.setText(MainActivity.utenteLoggato.email);

        storageReference= FirebaseStorage.getInstance().getReference();
        updateNavHeader();

        //LOGIN STUDENTE
        if(MainActivity.utenteLoggato.matricola.charAt(0) != '0')
        {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_casiDiStudio).setVisible(false);
        }
        else//LOGIN PROFESSORE
        {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_crea_gruppi).setVisible(false);
            toastMessage("ci sono");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Log.i(" "+String.valueOf(requestCode)+ resultCode,"Richiesta");
            if(resultCode == RESULT_OK && data != null){
                if (Home.galleria == true) {
                    selectedImage = data.getData();
                    ImageView imageView =findViewById(R.id.imageViewLogo);
                    imageView.setImageURI(selectedImage);
                    Home.galleria = false;
                }else if(Home.camera == true){
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(Home.this.getContentResolver(), photo, "Title", null);
                    ImageView imageView =findViewById(R.id.imageViewLogo);
                    imageView.setImageURI(Uri.parse(path));
                    selectedImage=Uri.parse(path);
                    Home.camera = false;
                }
            }
            uploadimage();
    }

    private void uploadimage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading image");
        pd.show();

        final String randomkey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("fotoProfilo/" + MainActivity.utenteLoggato.matricola+ ".jpg");

        riversRef.putFile(selectedImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        // Get a URL to the uploaded content
                        Snackbar.make(findViewById(android.R.id.content),"image uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"failed upload",Toast.LENGTH_LONG).show();
                    }
                })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progressPercent = (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    pd.setMessage("Progress"+ (int) progressPercent + "%");
                }
            });
    }

    public void updateNavHeader(){

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView navUserPhoto = headerView.findViewById(R.id.imageViewIcon);

        // Create a reference to a file from a Google Cloud Storage URI
        //
        // Reference to an image file in Cloud

        profile = storageReference.child("/fotoProfilo/"+ MainActivity.utenteLoggato.matricola+ ".jpg");
        profile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(navUserPhoto);
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}