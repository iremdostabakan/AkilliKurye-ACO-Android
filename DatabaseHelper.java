package com.example.kargorota;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KargoRota.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Son yapılan başarılı rotaları saklamak için tablo oluşturuyoruz
        db.execSQL("CREATE TABLE son_rota (id INTEGER PRIMARY KEY AUTOINCREMENT, rota_metni TEXT, toplam_mesafe TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS son_rota");
        onCreate(db);
    }

    // Son rotayı veri tabanına kaydeden fonksiyon
    public void rotaKaydet(String rotaMetni, String mesafe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rota_metni", rotaMetni);
        values.put("toplam_mesafe", mesafe);

        // Tabloyu temizleyip sadece en son rotayı tutalım
        db.delete("son_rota", null, null);
        db.insert("son_rota", null, values);
        db.close();
    }
}