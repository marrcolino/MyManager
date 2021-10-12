package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Animation topAnim, bottomAnim;
    private ImageView img;
    private TextView textView;
    public static DBHelper DB;
    public static User utenteLoggato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //utenteLoggato=new User();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        img = (ImageView)findViewById(R.id.imageView1);
        textView = (TextView)findViewById(R.id.textView1);

        img.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        DB = new DBHelper(this);

        if(MainActivity.utenteLoggato.matricola != "")
        {
            MainActivity.utenteLoggato.fotoProfilo= null;
            MainActivity.utenteLoggato.matricola = "";
            MainActivity.utenteLoggato.nome = "";
            MainActivity.utenteLoggato.cognome = "";
            MainActivity.utenteLoggato.email = "";
            MainActivity.utenteLoggato.password = "";
            MainActivity.utenteLoggato.dataNascita = "";
        }

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}