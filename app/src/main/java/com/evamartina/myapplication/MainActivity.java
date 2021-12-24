package com.evamartina.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    float textSize = 0;
    String pilih = "", image = "";
    TextView textNama;
    EditText nama, sinopsis, pemeran, tanggal;
    TextView txtSeekBar;
    SeekBar seekBar;
    RadioGroup pilihan;
    CheckBox horror, romance, action, fiction, comedy, mystery;
    DatePickerDialog datePickerDialog;

    final int kodeGallery = 100;
    Uri imageUri;
    ImageView imageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        nama = findViewById(R.id.nama);
        textNama = findViewById(R.id.textnama);
        sinopsis = findViewById(R.id.sinopsis);
        pemeran = findViewById(R.id.pemeran);
        txtSeekBar = findViewById(R.id.ratingvalue);
        horror = findViewById(R.id.horror);
        romance = findViewById(R.id.romance);
        action = findViewById(R.id.action);
        fiction = findViewById(R.id.fiction);
        comedy = findViewById(R.id.comedy);
        mystery = findViewById(R.id.mystery);
        seekBar = findViewById(R.id.seekBar);
        tanggal = findViewById(R.id.tanggal);
        imageView = findViewById(R.id.imageView);
        txtSeekBar.setText(Float.toString(textSize));

        seekBar.setProgress(0);
        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                float progressD = (float) progressValue/2;
                txtSeekBar.setText(Float.toString(progressD));
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tanggal.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        pilihan = findViewById(R.id.pilihan);
        pilihan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.film:
                        pilih = "Film";
                        textNama.setText("Nama Film");
                        nama.setHint("Masukkan Nama Film ");
                        sinopsis.setHint("Masukkan Sinopsis dari Film");
                        break;
                    case R.id.series:
                        pilih = "Series";
                        textNama.setText("Nama Series");
                        nama.setHint("Masukkan Nama Series ");
                        sinopsis.setHint("Masukkan Sinopsis dari Series");
                }
            }
        });
    }

    public void submit(View view) {
        showDialog();
    }
    private void showDialog(){
        String genre = "";
        if(horror.isChecked())
            genre += "Horror ";
        if(romance.isChecked())
            genre += "Romance ";
        if(action.isChecked())
            genre += "Action ";
        if(fiction.isChecked())
            genre += "Fiction ";
        if(comedy.isChecked())
            genre += "Comedy ";
        if(mystery.isChecked())
            genre += "Mystery ";

        if(nama.getText().toString().isEmpty() || sinopsis.getText().toString().isEmpty() ||
                pilih.equals("") || pemeran.getText().toString().isEmpty() || tanggal.getText().toString().isEmpty() || genre.equals("")) {
            Toast.makeText(getApplicationContext(),"Data belum lengkap",Toast.LENGTH_SHORT).show();
        }
        else {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SetterGetterData sgd = new SetterGetterData();
            sgd.setKategori(pilih);
            sgd.setNama(nama.getText().toString());
            sgd.setGenre(genre);
            sgd.setSinopsis(sinopsis.getText().toString());
            sgd.setTanggal(tanggal.getText().toString());
            sgd.setPemeran(pemeran.getText().toString());
            sgd.setRating(Float.parseFloat(txtSeekBar.getText().toString()));
            sgd.setImage(image);
            boolean input;
            dbHelper.tambahData(sgd);
            dbHelper.close();

            final DBHelper dh = new DBHelper(getApplicationContext());
            dh.dapatkanDataTeratas();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Data "+sgd.getKategori());
            alertDialogBuilder
                    .setMessage("Nama "+sgd.getKategori()+": " + sgd.getNama()+
                            "\nGenre: " +sgd.getGenre()+
                            "\nSinopsis: " + sgd.getSinopsis()+
                            "\nTanggal Rilis: " +sgd.getTanggal()+
                            "\nPemeran: " + sgd.getPemeran()+
                            "\nRating: " + sgd.getRating())
                    .setIcon(R.drawable.file)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivity.this, ViewData.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    public void upload(View view) {
        Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGallery, kodeGallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == kodeGallery && resultCode == RESULT_OK) {
            imageUri = data.getData();
            image = imageUri.toString();
            imageView.setImageURI(imageUri);
        }
    }

    public void backButton(View view) {
        onBackPressed();
    }
}