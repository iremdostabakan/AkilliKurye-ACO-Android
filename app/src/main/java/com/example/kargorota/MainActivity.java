package com.example.kargorota;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Arayüz elemanlarımızı tanımlıyoruz
    private Button btnOptimize, btnYeniRota;
    private TextView tvSonuc;

    // 13 adet adres kutucuğu için dizi oluşturuyoruz
    private CheckBox[] kutucuklar = new CheckBox[13];
    private int[] kutucukIDleri = {
            R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7,
            R.id.cb8, R.id.cb9, R.id.cb10, R.id.cb11, R.id.cb12, R.id.cb13, R.id.cb14
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML'deki elemanları Java koduna bağlıyoruz
        btnOptimize = findViewById(R.id.btnOptimize);
        btnYeniRota = findViewById(R.id.btnYeniRota);
        tvSonuc = findViewById(R.id.tvSonuc);

        // Döngü ile tüm CheckBox'ları tanımlıyoruz
        for (int i = 0; i < kutucukIDleri.length; i++) {
            kutucuklar[i] = findViewById(kutucukIDleri[i]);
        }

        // Optimize Et butonuna tıklandığında çalışacak olaylar
        btnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> secilenNoktalar = new ArrayList<>();

                for (int i = 0; i < kutucuklar.length; i++) {
                    if (kutucuklar[i].isChecked()) {
                        secilenNoktalar.add(i + 1);
                    }
                }

                AntColony karincaKolonisi = new AntColony();
                String sonucMetni = karincaKolonisi.rotayiHesapla(secilenNoktalar);
                tvSonuc.setText(sonucMetni);
            }
        });

        // Yeni Rota (Temizle) butonuna tıklandığında çalışacak olaylar
        btnYeniRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tüm kutucukların tikini kaldır
                for (int i = 0; i < kutucuklar.length; i++) {
                    if(kutucuklar[i] != null) {
                        kutucuklar[i].setChecked(false);
                    }
                }
                // Sonuç ekranını ilk haline döndür
                tvSonuc.setText("Hesaplanan en kısa teslimat rotası burada bir fiş gibi listelenecek...");
            }
        });
    }
}