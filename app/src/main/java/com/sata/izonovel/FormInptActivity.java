package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sata.izonovel.Model.ForminputRequest;
import com.sata.izonovel.Model.ForminputResponse;
import com.sata.izonovel.Retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormInptActivity extends AppCompatActivity {
    String[] genre = {"Action", "Romance", "Fantasi", "Horor", "Comedy", "Sci-fi"};
    TextInputEditText Judul, Pengarang, Penerbit, Tahunterbit, Sinopsis;
    AutoCompleteTextView Genre;
    Button simpanNovel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_inpt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, genre);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.filled_exposed_dropdown);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

        Judul= findViewById(R.id.met_judul);
        Pengarang= findViewById(R.id.met_pengarang);
        Penerbit= findViewById(R.id.met_penerbit);
        Tahunterbit= findViewById(R.id.met_tahun_terbit);
        Genre= findViewById(R.id.filled_exposed_dropdown);
        Sinopsis= findViewById(R.id.met_sinopsis);
        simpanNovel= findViewById(R.id.btnsave);

        simpanNovel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveList();
            }
        });
    }
        private void onSaveList() {
            ForminputRequest forminputRequest = new ForminputRequest();
            forminputRequest.setCollection("novel");
            forminputRequest.setDataSource("Cluster0");
            forminputRequest.setDatabase("izonovel");

            ForminputRequest.Document document = new ForminputRequest.Document();
            document.setJudul(Judul.getText().toString());
            document.setPengarang(Pengarang.getText().toString());
            document.setPenerbit(Penerbit.getText().toString());
            document.setTahunTerbit(Tahunterbit.getText().toString());
            document.setGenre(Genre.getText().toString());
            document.setSinopsis(Sinopsis.getText().toString());
            forminputRequest.setDocument(document);

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Info");
            progressDialog.setMessage("Save Data...");
            progressDialog.show();

            ApiService.endPoint().onSaveList(forminputRequest).enqueue(new Callback<ForminputResponse>() {
                @Override
                public void onResponse(Call<ForminputResponse> call, Response<ForminputResponse> response) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormInptActivity.this);
                    builder.setTitle("Info");
                    builder.setMessage("Data berhasil disimpan");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Judul.setText("");
                            Pengarang.setText("");
                            Penerbit.setText("");
                            Tahunterbit.setText("");
                            Genre.setText("");
                            Sinopsis.setText("");
                        }
                    });

                    builder.show();
                }

                @Override
                public void onFailure(Call<ForminputResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }
    }
