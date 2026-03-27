package com.example.kargorota;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 3 saniye (3000 milisaniye) bekleyip ana ekrana geçiş yapıyoruz
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // SplashActivity'den MainActivity'e geçiş niyeti (Intent)
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Geri tuşuna basıldığında tekrar açılış ekranına dönmemesi için bu ekranı kapatıyoruz
                finish();
            }
        }, 3000);
    }
}