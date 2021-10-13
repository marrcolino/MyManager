package com.example.mymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "DatabaseMyManager.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Studente (matricola TEXT primary key, nome TEXT, cognome TEXT, email TEXT, password TEXT, dataNascita TEXT)");
        DB.execSQL("create Table Professore ( matricola TEXT primary key, nome TEXT, cognome TEXT, email TEXT, password TEXT, dataNascita TEXT)");
        DB.execSQL("create Table CasoDiStudio ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descrizione TEXT, esame TEXT, matricolaProfessore TEXT, FOREIGN KEY (matricolaProfessore) REFERENCES Professore(matricola))");
        DB.execSQL("create Table Gruppo ( ID INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, matricolaPartecipante1 TEXT, matricolaPartecipante2 TEXT, matricolaPartecipante3 TEXT, matricolaPartecipante4 TEXT, IDCasoStudio INTEGER, FOREIGN KEY (IDCasoStudio) REFERENCES CasoDiStudio(ID), FOREIGN KEY (matricolaPartecipante1) REFERENCES Studente(matricola), FOREIGN KEY (matricolaPartecipante2) REFERENCES Studente(matricola), FOREIGN KEY (matricolaPartecipante3) REFERENCES Studente(matricola), FOREIGN KEY (matricolaPartecipante4) REFERENCES Studente(matricola))");
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

    public Boolean updateUserData(String matricola ,String nome , String cognome , String email , String password , String dataNascita ) {
        int result;
        if (matricola.charAt(0) != '0') {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("matricola", matricola);
            contentValues.put("nome", nome);
            contentValues.put("cognome", cognome);
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("dataNascita", dataNascita);
            //long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});
            result = DB.update("Studente", contentValues, "matricola=?", new String[]{matricola});
        } else {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("matricola", matricola);
            contentValues.put("nome", nome);
            contentValues.put("cognome", cognome);
            contentValues.put("email", email);
            contentValues.put("password", password);
            contentValues.put("dataNascita", dataNascita);
            result = DB.update("Professore", contentValues, "matricola=?", new String[]{matricola});
        }
        return result!= -1;
    }

    public Cursor login (String matricola, String password){
        Cursor cursor;
        SQLiteDatabase DB = this.getWritableDatabase();

        if(matricola.charAt(0)!='0') {
            cursor = DB.rawQuery("Select * FROM Studente WHERE matricola = '" + matricola + "' AND password  = '" + password +"'",null);

        }else{
            cursor = DB.rawQuery("Select * FROM Professore WHERE matricola = '" + matricola + "' AND password  = '" + password +"'",null);
        }
        return cursor;
    }

    public Cursor getData(String matricola){
        Cursor cursor;
        SQLiteDatabase DB = this.getWritableDatabase();
        if(matricola.charAt(0)!='0'){
             cursor = DB.rawQuery("Select * FROM Studente WHERE matricola = '"+matricola+"'",null);
        }else{
             cursor = DB.rawQuery("Select * FROM Professore WHERE matricola = '"+matricola+"'" ,null);
        }
        return cursor;
    }

   /* public Cursor readAllData(){
        String query = "SELECT * FROM Userdetails " + null;
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = null;
        if(DB != null){
            cursor = DB.rawQuery(query, null);
        }
        return cursor;
    }*/
}