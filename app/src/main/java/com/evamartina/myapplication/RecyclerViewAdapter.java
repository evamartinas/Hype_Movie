package com.evamartina.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList idList;
    private ArrayList kategoriList;
    private ArrayList namaList;
    private ArrayList genreList;
    private ArrayList sinopsisList;
    private ArrayList tanggalList;
    private ArrayList pemeranList;
    private ArrayList ratingList;
    private ArrayList imageList;
    private Context context;
    View V;

    RecyclerViewAdapter(ArrayList idList, ArrayList kategoriList, ArrayList namaList, ArrayList genreList, ArrayList sinopsisList, ArrayList tanggalList, ArrayList pemeranList, ArrayList ratingList, ArrayList imageList){
        this.idList = idList;
        this.kategoriList = kategoriList;
        this.namaList = namaList;
        this.genreList = genreList;
        this.sinopsisList = sinopsisList;
        this.tanggalList = tanggalList;
        this.pemeranList = pemeranList;
        this.ratingList = ratingList;
        this.imageList = imageList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Kategori, Nama, Rating, Genre, Pemeran;
        private ImageButton edit, delete;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            Kategori = itemView.findViewById(R.id.kategori);
            Nama = itemView.findViewById(R.id.nama);
            Rating = itemView.findViewById(R.id.rating);
            Genre = itemView.findViewById(R.id.genre);
            Pemeran = itemView.findViewById(R.id.pemeran);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        V = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        int whiteColor = Color.parseColor("#B3FFFFFF");
        int blueColor = Color.parseColor("#B39DD1D7");
        final int Id = (Integer) idList.get(position);
        final String Kategori = (String) kategoriList.get(position);
        final String Nama = (String) namaList.get(position);
        final String Genre = (String) genreList.get(position);
        final String Sinopsis = (String) sinopsisList.get(position);
        final String Tanggal = (String) tanggalList.get(position);
        final String Pemeran = (String) pemeranList.get(position);
        final float Rating = (Float) ratingList.get(position);
        final String Image = (String) imageList.get(position);
        holder.Kategori.setText(Kategori);
        holder.Nama.setText(Nama);
        holder.Rating.setText("Rating: "+String.valueOf(Rating));
        holder.Genre.setText("Genre: "+Genre);
        holder.Pemeran.setText("Pemeran: "+Pemeran);

        if (position%2 == 0){
            V.setBackgroundColor(blueColor);
        } else {
            V.setBackgroundColor(whiteColor);
        }

        V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), Result.class);
                intent.putExtra("id", Id);
                intent.putExtra("kategori", Kategori);
                intent.putExtra("nama", Nama);
                intent.putExtra("genre", Genre);
                intent.putExtra("sinopsis", Sinopsis);
                intent.putExtra("tanggal", Tanggal);
                intent.putExtra("pemeran", Pemeran);
                intent.putExtra("rating", Rating);
                intent.putExtra("image", Image);
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(context, EditData.class);
                intent.putExtra("id", Id);
                intent.putExtra("kategori", Kategori);
                intent.putExtra("nama", Nama);
                intent.putExtra("genre", Genre);
                intent.putExtra("sinopsis", Sinopsis);
                intent.putExtra("tanggal", Tanggal);
                intent.putExtra("pemeran", Pemeran);
                intent.putExtra("rating", Rating);
                intent.putExtra("image", Image);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String radiokategori = Kategori;
                String message ="Yakin menghapus data "+radiokategori+"?";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Hapus Data");
                alertDialogBuilder
                        .setMessage(message)
                        .setIcon(R.drawable .file)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                DBHelper dbHelper = new DBHelper(context.getApplicationContext());
                                dbHelper.hapusData(Id);
                                Toast.makeText(context.getApplicationContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                dbHelper.close();
                                Intent intent = new Intent(context, ViewData.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

}
