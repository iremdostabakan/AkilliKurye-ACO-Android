package com.example.kargorota;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnOptimize, btnYeniRota;
    private TextView tvSonuc;
    private CheckBox[] checkBoxes = new CheckBox[13];
    private final int[] checkBoxIds = {
            R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7,
            R.id.cb8, R.id.cb9, R.id.cb10, R.id.cb11, R.id.cb12, R.id.cb13, R.id.cb14
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Initialization
        btnOptimize = findViewById(R.id.btnOptimize);
        btnYeniRota = findViewById(R.id.btnYeniRota);
        tvSonuc = findViewById(R.id.tvSonuc);

        for (int i = 0; i < checkBoxIds.length; i++) {
            checkBoxes[i] = findViewById(checkBoxIds[i]);
        }

        // Logic
        btnOptimize.setOnClickListener(v -> {
            List<Integer> selectedNodes = new ArrayList<>();
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isChecked()) {
                    selectedNodes.add(i + 1);
                }
            }

            AntColony aco = new AntColony();
            tvSonuc.setText(aco.calculateRoute(selectedNodes));
        });

        btnYeniRota.setOnClickListener(v -> {
            for (CheckBox cb : checkBoxes) {
                if (cb != null) cb.setChecked(false);
            }
            tvSonuc.setText("Route info will be listed here...");
        });
    }
}