package com.sata.izonovel;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sata.izonovel.Model.InsertResponseModel;
import com.sata.izonovel.Model.RegisterRequestModel;
import com.sata.izonovel.Retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText firstname, lastname, email, password;
    Button btsubmitbutton, btcancelbutton;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btsubmitbutton = findViewById(R.id.btsubmitbutton);
        btcancelbutton = findViewById(R.id.btcancelbutton);

        btsubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitRegister();
            }
        });
    }

    private void onSubmitRegister() {
        RegisterRequestModel registerRequestModel = new RegisterRequestModel();
        registerRequestModel.setDataSource("Cluster0");
        registerRequestModel.setDatabase("izonovel");
        registerRequestModel.setCollection("users");

        RegisterRequestModel.Document document = new RegisterRequestModel.Document();
        String FullName = firstname.getText().toString() +" "+ lastname.getText().toString();
        document.setFullName(FullName);
        document.setUsername(email.getText().toString());
        document.setPassword(password.getText().toString());

        registerRequestModel.setDocument(document);

        progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Info");
        progressDialog.setMessage("Submitting Data...");
        progressDialog.show();


        ApiService.endPoint().registerUser(registerRequestModel).enqueue(new Callback<InsertResponseModel>() {
            @Override
            public void onResponse(Call<InsertResponseModel> call, Response<InsertResponseModel> response) {
            progressDialog.dismiss();
            String responseId =  response.body().getInsertedId().toString();

            String pesan = "Data berhasil disimpang dengan ID"+responseId;
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("Info");
            builder.setMessage(pesan)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            ClearForm();
                        }
                    });
            builder.show();
            }

            @Override
            public void onFailure(Call<InsertResponseModel> call, Throwable t) {
                progressDialog.dismiss();

                Log.d("ERR registerUser", t.toString());
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void ClearForm(){
        firstname.setText("");
        lastname.setText("");
        email.setText("");
        password.setText("");
    }
}

