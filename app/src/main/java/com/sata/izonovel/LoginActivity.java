package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sata.izonovel.Model.LoginRequestModel;
import com.sata.izonovel.Model.LoginResponseModel;
import com.sata.izonovel.Retrofit.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
EditText username, password;
Button btnlogin;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitLogin();
            }
        });
    }
    private void onSubmitLogin() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setCollection("users");
        loginRequestModel.setDatabase("izonovel");
        loginRequestModel.setDataSource("Cluster0");

        LoginRequestModel.Filter loginFilter = new LoginRequestModel.Filter();
        loginFilter.setUsername(username.getText().toString());
        loginFilter.setPassword(password.getText().toString());

        loginRequestModel.setFilter(loginFilter);

        // Menampilkan loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Info");
        progressDialog.setMessage("Submiting Data....");
        progressDialog.show();

        // manggil url service login (api login)
        ApiService.endPoint().onSubmitLogin(loginRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                //kondisi jika berhasil login
                if(response.body().getDocument() != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Username dan password tidak ditemukan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                //kondisi jika gagal login ketika hit api
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }
}