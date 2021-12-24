package com.evamartina.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "hypemovie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE hypemovie_table(id INTEGER PRIMARY KEY AUTOINCREMENT, kategori TEXT, nama TEXT, genre TEXT, sinopsis TEXT, tanggal TEXT, pemeran TEXT, rating FLOAT, image TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS hypemovie_table");
    }

    public boolean tambahData(SetterGetterData sgd) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("nama", sgd.getNama());
        cv.put("genre", sgd.getGenre());
        cv.put("sinopsis", sgd.getSinopsis());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("pemeran", sgd.getPemeran());
        cv.put("rating", sgd.getRating());
        cv.put("image", sgd.getImage());

        return db.insert("hypemovie_table", null, cv) > 0;
    }

    public Cursor dapatkanSemuaData() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + "hypemovie_table", null);
    }

    public Cursor dapatkanDataTeratas() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + "hypemovie_table" + " order by id desc limit 1", null);
    }

    public boolean perbaharuiData(SetterGetterData sgd, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("nama", sgd.getNama());
        cv.put("genre", sgd.getGenre());
        cv.put("sinopsis", sgd.getSinopsis());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("pemeran", sgd.getPemeran());
        cv.put("rating", sgd.getRating());
        cv.put("image", sgd.getImage());
        return db.update("hypemovie_table", cv, "id" + "=" + id,
                null) > 0;
    }

    public void hapusData (int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("hypemovie_table", "id" + "=" + id, null);
    }
}