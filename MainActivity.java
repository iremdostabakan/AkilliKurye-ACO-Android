package com.example.kargorota;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnOptimize, btnYeniRota;
    private TextView tvSonuc, tvHavaDurumu; // tvHavaDurumu: API için eklendi
    private DatabaseHelper dbHelper; // Veri yönetimi için eklendi
    private CheckBox[] checkBoxes = new CheckBox[13];
    private final int[] checkBoxIds = {
            R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7,
            R.id.cb8, R.id.cb9, R.id.cb10, R.id.cb11, R.id.cb12, R.id.cb13, R.id.cb14
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Veri tabanı nesnesini başlatıyoruz
        dbHelper = new DatabaseHelper(this);


        btnOptimize = findViewById(R.id.btnOptimize);
        btnYeniRota = findViewById(R.id.btnYeniRota);
        tvSonuc = findViewById(R.id.tvSonuc);
        tvHavaDurumu = findViewById(R.id.tvHavaDurumu);

        for (int i = 0; i < checkBoxIds.length; i++) {
            checkBoxes[i] = findViewById(checkBoxIds[i]);
        }

        // Dış Servis Entegrasyonu: Arka planda hava durumu API'sini çağırıyoruz
        new FetchWeatherTask().execute("https://api.open-meteo.com/v1/forecast?latitude=40.35&longitude=27.97&current_weather=true");

        // Rota Optimizasyon Mantığı
        btnOptimize.setOnClickListener(v -> {
            List<Integer> selectedNodes = new ArrayList<>();
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isChecked()) {
                    selectedNodes.add(i + 1);
                }
            }

            AntColony aco = new AntColony();
            String sonucMtf = aco.calculateRoute(selectedNodes);

            // 1. Veri Yönetimi: Başarılı rotayı yerel SQLite veri tabanına kaydediyoruz
            dbHelper.rotaKaydet(sonucMtf, "Hesaplandı");

            // 2. Aktivite Geçişi: Sonucu yeni ekranda (ResultActivity) gösteriyoruz
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("OPTIMIZED_ROUTE", sonucMtf);
            startActivity(intent);
        });

        btnYeniRota.setOnClickListener(v -> {
            for (CheckBox cb : checkBoxes) {
                if (cb != null) cb.setChecked(false);
            }
            tvSonuc.setText("Route info will be listed here...");
        });
    }

    // Dış Servis Entegrasyonu (API) için Arka Plan Görevi (AsyncTask)
    private class FetchWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    // API'den dönen JSON verisini ayıklıyoruz
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject currentWeather = jsonObject.getJSONObject("current_weather");
                    double temperature = currentWeather.getDouble("temperature");
                    tvHavaDurumu.setText("🌤️ Bölge Sıcaklığı: " + temperature + "°C");
                } catch (Exception e) {
                    tvHavaDurumu.setText("🌤️ Hava durumu yüklenemedi.");
                }
            } else {
                tvHavaDurumu.setText("🌤️ Bağlantı hatası.");
            }
        }
    }
}