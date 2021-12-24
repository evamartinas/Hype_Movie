package com.evamartina.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditData extends AppCompatActivity {

    float textSize = 0;
    String pilih = "", image = "";
    TextView textNama;
    EditText nama, sinopsis, pemeran, tanggal;
    TextView txtSeekBar;
    SeekBar seekBar;
    RadioGroup pilihan;
    RadioButton film, series;
    CheckBox horror, romance, action, fiction, comedy, mystery;
    DatePickerDialog datePickerDialog;

    final int kodeGallery = 100;
    Uri imageUri;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
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
        film = findViewById(R.id.film);
        series = findViewById(R.id.series);
        txtSeekBar.setText(Float.toString(textSize));

        pilih = getIntent().getExtras().getString("kategori");
        if(pilih.equals("Film")){
            film.setChecked(true);
        }
        else{
            series.setChecked(true);
        }

        nama.setText(getIntent().getExtras().getString("nama"));
        sinopsis.setText(getIntent().getExtras().getString("sinopsis"));
        tanggal.setText(getIntent().getExtras().getString("tanggal"));
        pemeran.setText(getIntent().getExtras().getString("pemeran"));

        String genres = getIntent().getExtras().getString("genre");
        String[] genre = genres.split(" ");
        for (String s : genre) {
            switch (s) {
                case "Horror":
                    horror.setChecked(true);
                    break;
                case "Romance":
                    romance.setChecked(true);
                    break;
                case "Action":
                    action.setChecked(true);
                    break;
                case "Fiction":
                    fiction.setChecked(true);
                    break;
                case "Comedy":
                    comedy.setChecked(true);
                    break;
                case "Mystery":
                    mystery.setChecked(true);
                    break;
            }
        }

        txtSeekBar.setText(String.valueOf(getIntent().getExtras().getFloat("rating")));
        seekBar.setProgress((int) ((getIntent().getExtras().getFloat("rating"))*2));
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
                txtSeekBar.setText(String.valueOf(progressD));
            }
        });

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(EditData.this,
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

    public void submit(View view) {
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
        }else {
            int id = getIntent().getExtras().getInt("id");
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
            dbHelper.perbaharuiData(sgd, id);
            dbHelper.close();
            Intent intent = new Intent(EditData.this, ViewData.class);
            startActivity(intent);
        }
    }

    public void backButton(View view) {
        onBackPressed();
    }
}