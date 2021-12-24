package com.evamartina.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {

    TextView kategori, nama, genre, sinopsis, tanggal, pemeran, rating, textnama;
    ImageView imageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        kategori = findViewById(R.id.kategori);
        textnama = findViewById(R.id.textnama);
        nama = findViewById(R.id.nama);
        genre = findViewById(R.id.genre);
        sinopsis = findViewById(R.id.sinopsis);
        tanggal = findViewById(R.id.tanggal);
        pemeran = findViewById(R.id.pemeran);
        rating = findViewById(R.id.rating);
        imageView = findViewById(R.id.imageView);

        kategori.setText("Data " +getIntent().getExtras().getString("kategori"));
        textnama.setText("Nama "+getIntent().getExtras().getString("kategori"));
        nama.setText(getIntent().getExtras().getString("nama"));
        genre.setText(getIntent().getExtras().getString("genre"));
        sinopsis.setText(getIntent().getExtras().getString("sinopsis"));
        tanggal.setText(getIntent().getExtras().getString("tanggal"));
        pemeran.setText(getIntent().getExtras().getString("pemeran"));
        rating.setText(String.valueOf(getIntent().getExtras().getFloat("rating")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Application On Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Application On Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Application On Restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Application On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Application On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Selamat Tinggal", Toast.LENGTH_SHORT).show();
    }

    public void edit(View view) {
    }

    public void hapus(View view) {
    }

    public void backButton(View view) {
        onBackPressed();
    }
}