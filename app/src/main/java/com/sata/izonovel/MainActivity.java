package com.sata.izonovel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout menuInfoPengguna, menu_one, detail_nov;
    TextView favorite_novel,novel,inputnovel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuInfoPengguna = findViewById(R.id.informasi_pengguna);
        menuInfoPengguna.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
                startActivity(intent);
                return false;
            }
        });
        favorite_novel = findViewById(R.id.favorite_novel);
        favorite_novel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, favorite.class);
                startActivity(intent);
                return false;
            }
        });
        menu_one = findViewById(R.id.menu_one);
        menu_one.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, DaftarNovel.class);
                startActivity(intent);
                return false;

            }
        });
        detail_nov = findViewById(R.id.detail_nov);
        detail_nov.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, Detail_Novel.class);
                startActivity(intent);
                return false;
            }
        });
        inputnovel = findViewById(R.id.inputnovel);
        inputnovel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(MainActivity.this, FormInptActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }
}