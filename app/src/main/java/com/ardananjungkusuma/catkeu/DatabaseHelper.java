package com.ardananjungkusuma.catkeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "catkeuSQLite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id_user integer PRIMARY KEY AUTOINCREMENT, username text, password text)");
        db.execSQL("CREATE TABLE keuangan(id_keuangan integer PRIMARY KEY AUTOINCREMENT, tanggal text, nominal text, keterangan text, kategori text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS keuangan");
        onCreate(db);
    }

    // session check
    public Boolean sessionCheck(String sessionValues){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{sessionValues});
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    // count session
    public int sessionCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session", null);
        if(cursor.getCount() > 0){
            return 1;
        } else {
            return 0;
        }
    }

    // insert user
    public Boolean createSession(String sessionValues, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("login", sessionValues);
        long insert = db.insert("session", null, contentValues);
        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }

    // session update
    public Boolean updateSession(String sessionValues, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login",sessionValues);
        long update = db.update("session",contentValues, "id="+id,null);
        if(update == -1){
            return false;
        } else {
            return true;
        }
    }

    // insert user
    public Boolean insertUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }

    // auth check
    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?", new String[]{username, password});
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkPass(String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE id_user = '1' AND password = ?", new String[]{password});
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean updatePassword(String password, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        long update = db.update("user",contentValues, "id_user="+id,null);
        if(update == -1){
            return false;
        } else {
            return true;
        }
    }

    // tambah data keuangan
    public Boolean insertDataKeuangan(String tanggal, String nominal, String keterangan, String kategori){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tanggal", tanggal);
        contentValues.put("nominal", nominal);
        contentValues.put("keterangan", keterangan);
        contentValues.put("kategori", kategori);
        long insert = db.insert("keuangan", null, contentValues);
        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }
}
