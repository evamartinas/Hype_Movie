package com.evamartina.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    private DBHelper MyDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList idList;
    private ArrayList kategoriList;
    private ArrayList namaList;
    private ArrayList genreList;
    private ArrayList sinopsisList;
    private ArrayList tanggalList;
    private ArrayList pemeranList;
    private ArrayList ratingList;
    private ArrayList imageList;
    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        idList = new ArrayList<>();
        kategoriList = new ArrayList<>();
        namaList = new ArrayList<>();
        genreList = new ArrayList<>();
        sinopsisList = new ArrayList<>();
        tanggalList = new ArrayList<>();
        pemeranList = new ArrayList<>();
        ratingList = new ArrayList<>();
        imageList = new ArrayList<>();
        MyDatabase = new DBHelper(getBaseContext());
        recyclerView = findViewById(R.id.recycler);
        noData = findViewById(R.id.textData);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(idList, kategoriList, namaList, genreList, sinopsisList, tanggalList, pemeranList, ratingList, imageList);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() != 0){
            noData.setVisibility(View.GONE);
        }
    }

    @SuppressLint("Recycle")
    protected void getData(){
        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.dapatkanSemuaData();
        cursor.moveToFirst();

        for(int count=0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
            idList.add(cursor.getInt(0));//Menambil Data Dari Kolom 0 (Id)
            kategoriList.add(cursor.getString(1));//Menambil Data Dari Kolom 1 (Kategori)
            namaList.add(cursor.getString(2));//Menambil Data Dari Kolom 2 (Jumlah Uang)
            genreList.add(cursor.getString(3));//Menambil Data Dari Kolom 4 (Tanggal)
            sinopsisList.add(cursor.getString(4));//Menambil Data Dari Kolom 3 (Deskripsi)
            tanggalList.add(cursor.getString(5));//Menambil Data Dari Kolom 5 (Skala Prioritas)
            pemeranList.add(cursor.getString(6));//Menambil Data Dari Kolom 5 (Skala Prioritas)
            ratingList.add(cursor.getFloat(7));//Menambil Data Dari Kolom 5 (Skala Prioritas)
            imageList.add(cursor.getString(8));//Menambil Data Dari Kolom 5 (Skala Prioritas)
        }
    }

    public void tambah(View view) {
        Intent intent = new Intent(ViewData.this, MainActivity.class);
        startActivity(intent);
    }
}