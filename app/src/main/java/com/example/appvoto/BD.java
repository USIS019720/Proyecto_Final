package com.example.appvoto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper
{
    static String nombreDB = "db_voto";
    static String tblVoto = "CREATE TABLE tblVoto(dato1 int, dato2 int, texto text)";
    static String drop = "drop table if exists tblVoto";
    // static String tblVoto = "CREATE TABLE tblVoto(idVoto integer primary key autoincrement, voto int, dui int)";

    public BD(@Nullable Context context) {
        super(context, nombreDB, null, 1); //CREATE DATABASE db_amigos; -> SQLite
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblVoto);
        // db.execSQL(drop);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // public void agregarDatos(Integer candidato, Integer dui){
    public void agregarDatos(Integer dato1, Integer dato2, String texto){
        SQLiteDatabase db = getWritableDatabase();

        if (db != null){
            db.execSQL("INSERT INTO tblVoto VALUES ('" + dato1 + "','" + dato2 + "','" + texto + "')");
            db.close();
        }
    }

    public Cursor getValues(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        c = db.rawQuery("select dato1,texto from tblVoto",null);
        // c = db.rawQuery("select count(voto) from tblVoto where voto = 1",null);

        return c;
    }
}

