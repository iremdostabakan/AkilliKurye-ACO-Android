package com.example.kargorota;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvGoster = findViewById(R.id.tvResultSonuc);
        Button btnGeri = findViewById(R.id.btnResultGeri);

        // MainActivity'den gönderilen optimize edilmiş rota verisini alıyoruz
        String gelenRota = getIntent().getStringExtra("OPTIMIZED_ROUTE");
        if (gelenRota != null) {
            tvGoster.setText(gelenRota);
        }

        // Ana ekrana geri dönme butonu tetikleyicisi
        btnGeri.setOnClickListener(v -> finish());
    }
}