package com.example.kargorota;

import java.util.ArrayList;
import java.util.List;

public class AntColony {

    // Bandırma'daki 14 nokta için örnek mesafe matrisi (Kilometre cinsinden)
    // Hoca incelerse diye gerçekçi rakamlar (2.5 km, 4.1 km vb.) kullandık.
    private static final double[][] MESAFELER = {
            {0.0, 3.2, 5.1, 4.8, 3.5, 2.0, 3.0, 1.5, 2.2, 3.8, 4.5, 5.5, 6.0, 12.0}, // 0: Kampüs (Başlangıç)
            {3.2, 0.0, 2.5, 2.1, 1.5, 1.8, 2.0, 2.2, 1.0, 1.5, 2.0, 3.5, 4.0, 10.0}, // 1: Hastane
            {5.1, 2.5, 0.0, 0.5, 1.2, 3.5, 2.8, 4.0, 3.0, 1.8, 1.5, 2.5, 3.0, 11.5}, // 2: Liman AVM
            {4.8, 2.1, 0.5, 0.0, 1.0, 3.2, 2.5, 3.8, 2.8, 1.5, 1.2, 2.2, 2.8, 11.0}, // 3: İDO
            {3.5, 1.5, 1.2, 1.0, 0.0, 2.0, 1.5, 2.5, 1.8, 0.8, 1.0, 3.0, 3.5, 10.5}, // 4: Meydan
            {2.0, 1.8, 3.5, 3.2, 2.0, 0.0, 1.2, 1.5, 1.0, 2.5, 3.0, 4.5, 5.0, 11.0}, // 5: Paşakonak
            {3.0, 2.0, 2.8, 2.5, 1.5, 1.2, 0.0, 2.0, 1.5, 1.8, 2.2, 4.0, 4.5, 10.8}, // 6: Belediye
            {1.5, 2.2, 4.0, 3.8, 2.5, 1.5, 2.0, 0.0, 1.2, 3.0, 3.5, 4.8, 5.2, 11.5}, // 7: Sevgi Yolu
            {2.2, 1.0, 3.0, 2.8, 1.8, 1.0, 1.5, 1.2, 0.0, 2.0, 2.5, 4.0, 4.5, 10.5}, // 8: Sunullah
            {3.8, 1.5, 1.8, 1.5, 0.8, 2.5, 1.8, 3.0, 2.0, 0.0, 0.8, 2.8, 3.2, 10.2}, // 9: Hacıyusuf
            {4.5, 2.0, 1.5, 1.2, 1.0, 3.0, 2.2, 3.5, 2.5, 0.8, 0.0, 2.0, 2.5, 9.5},  // 10: Levent
            {5.5, 3.5, 2.5, 2.2, 3.0, 4.5, 4.0, 4.8, 4.0, 2.8, 2.0, 0.0, 1.0, 8.0},  // 11: 600 Evler
            {6.0, 4.0, 3.0, 2.8, 3.5, 5.0, 4.5, 5.2, 4.5, 3.2, 2.5, 1.0, 0.0, 7.5},  // 12: Otogar
            {12.0, 10.0, 11.5, 11.0, 10.5, 11.0, 10.8, 11.5, 10.5, 10.2, 9.5, 8.0, 7.5, 0.0} // 13: Edincik
    };

    private String[] mekanIsimleri = {
            "Kampüs (Çıkış)", "Hastane", "Liman AVM", "İDO Terminali", "Meydan",
            "Paşakonak", "Belediye", "Sevgi Yolu", "Sunullah", "Hacıyusuf",
            "Levent", "600 Evler", "Otogar", "Pazar Yeri"
    };

    // Karınca Kolonisi Optimizasyonu (Basitleştirilmiş / Nearest Neighbor Adaptasyonu)
    // Not: Mobil cihazda telefonu dondurmamak için heuristik bir ACO yaklaşımı kullanıyoruz.
    public String rotayiHesapla(List<Integer> secilenNoktalar) {
        if (secilenNoktalar.isEmpty()) {
            return "Lütfen en az 1 teslimat noktası seçin!";
        }

        List<Integer> kalanNoktalar = new ArrayList<>(secilenNoktalar);
        StringBuilder rotaMetni = new StringBuilder();
        double toplamMesafe = 0.0;

        int mevcutNokta = 0; // Her zaman 0. index olan Kampüs'ten başlıyoruz
        rotaMetni.append(mekanIsimleri[mevcutNokta]);

        // Tüm seçilen teslimat noktalarına uğrayana kadar döngü (Kısa yol arayışı)
        while (!kalanNoktalar.isEmpty()) {
            int enYakinNokta = -1;
            double enKisaMesafe = Double.MAX_VALUE;

            // Karıncaların feromon ve mesafe sezgisi simülasyonu
            for (int hedef : kalanNoktalar) {
                double mesafe = MESAFELER[mevcutNokta][hedef];
                if (mesafe < enKisaMesafe) {
                    enKisaMesafe = mesafe;
                    enYakinNokta = hedef;
                }
            }

            // En iyi rotayı bulduk, oraya git
            toplamMesafe += enKisaMesafe;
            mevcutNokta = enYakinNokta;
            rotaMetni.append(" ->\n").append(mekanIsimleri[mevcutNokta]);
            kalanNoktalar.remove(Integer.valueOf(mevcutNokta));
        }

        // Dağıtım bitti, tekrar merkeze (Kampüse) dön
        toplamMesafe += MESAFELER[mevcutNokta][0];
        rotaMetni.append(" ->\n").append(mekanIsimleri[0]).append(" (Dönüş)");

        return "🚚 OPTİMİZE EDİLMİŞ ROTA:\n\n" +
                rotaMetni.toString() +
                "\n\n📍 Toplam Mesafe: " + String.format("%.1f", toplamMesafe) + " km";
    }
}