🚚 Akıllı Kurye: Karınca Kolonisi Optimizasyonu (ACO) & Karar Destek Sistemi
Bu proje, bir mobil uygulama üzerinden kuryelerin gün içindeki teslimat rotalarını Karınca Kolonisi Optimizasyonu (ACO) algoritması kullanarak en kısa ve verimli şekilde planlayan, modern mimariye sahip bir Android uygulamasıdır.


🌟 Projenin Amacı ve Özellikleri
Sıradan bir listeleme uygulamasının ötesinde, arka planda çalışan matematiksel bir optimizasyon motoruna sahiptir. Bandırma içindeki 14 farklı gerçek teslimat noktası (Kampüs, Hastane, Liman AVM vb.) arasında akıllı simülasyon yapar.

📱 Çoklu Aktivite Mimarisi: Uygulama kullanıcı deneyimini artırmak için en az 3 farklı ekrandan oluşur. Açılışta lila tonlarında bir SplashActivity, rota seçiminin yapıldığı MainActivity ve sonuçların listelendiği ResultActivity ekranları bulunur.

💾 Veri Yönetimi (Yerel Veri Tabanı): Uygulamada SQLite (DatabaseHelper) mimarisi kurulmuştur. Algoritmanın hesapladığı "En Son Başarılı Rota" ve toplam mesafe bilgileri yerel veri tabanında güvenli bir şekilde saklanır.

🌤️ Dış Servis Entegrasyonu (API): Kuryelerin saha operasyonlarını desteklemek amacıyla Open-Meteo API entegrasyonu yapılmıştır. Uygulama açıldığında arka planda (AsyncTask) anlık hava durumu verilerini çekerek kuryeye bölge sıcaklığını gösterir.

📐 Algoritmik Altyapı: Arayüz (Activity) ile optimizasyon algoritması (Java Class) mimarisi temiz bir şekilde birbirinden ayrılmıştır.

🛠️ Kullanılan Teknolojiler & Kütüphaneler
Dil/Geliştirme Ortamı: Java, Android Studio (XML Layouts, CardView, ScrollView, LinearLayout)

Veri Tabanı: SQLite (Yerel Veri Yönetimi için)

Ağ Servisleri: HttpURLConnection, JSON Parsing (Hava Durumu API'si için)

Algoritma: Heuristic Ant Colony Algorithm (Nearest Neighbor Adaptasyonu)
