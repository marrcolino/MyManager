package com.example.mymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "DatabaseMyManager.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Studente (matricola TEXT primary key, nome TEXT, cognome TEXT, email TEXT, password TEXT, dataNascita TEXT )");
        DB.execSQL("create Table Professore ( matricola TEXT primary key, nome TEXT, cognome TEXT, email TEXT, password TEXT, dataNascita TEXT)");
        DB.execSQL("create Table CasoDiStudio ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descrizione TEXT, esame TEXT, matricolaProfessore TEXT)");
        DB.execSQL("create Table Gruppo ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, matricolaPartecipante1 TEXT, matricolaPartecipante2 TEXT, matricolaPartecipante3 TEXT, matricolaPartecipante4 TEXT, IDCasoStudio INTEGER, FOREIGN KEY (IDCasoStudio) REFERENCES CasoDiStudio(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Studente");
        DB.execSQL("drop Table if exists Professore");
        DB.execSQL("drop Table if exists CasoDiStudio");
        DB.execSQL("drop Table if exists Gruppo");
        onCreate(DB);
    }
    public Boolean insertUserData(String matricola ,String nome , String cognome , String email , String password , String dataNascita ) {
        Long result;
        if (matricola.charAt(0) != '0') {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("matricola", matricola);
            contentValues.put("nome", nome);
            contentValues.put("cognome", cognome);
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("dataNascita", dataNascita);
            result = DB.insert("Studente", null, contentValues);
        } else {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("matricola", matricola);
            contentValues.put("nome", nome);
            contentValues.put("cognome", cognome);
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("dataNascita", dataNascita);
            result = DB.insert("Professore", null, contentValues);
        }
        return result!= -1;
    }
}