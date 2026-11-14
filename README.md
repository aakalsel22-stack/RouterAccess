# Router Access (Android)

Aplikasi Android sederhana untuk mendeteksi default gateway pada koneksi Wi-Fi dan membuka halaman admin router melalui browser.

## Cara membuka
1. Ekstrak ZIP ini.
2. Buka folder `RouterAccess` melalui Android Studio.
3. Tunggu Gradle Sync selesai.
4. Hubungkan HP ke Wi-Fi router yang ingin diakses.
5. Klik Run untuk memasang aplikasi ke HP.

## Cara menggunakan
- Tekan **Deteksi Ulang Gateway**.
- Periksa alamat yang terisi, misalnya `http://192.168.1.1`.
- Tekan **Buka Halaman Admin**.
- Masuk menggunakan kredensial admin router Anda.

## Catatan
- Aplikasi memerlukan koneksi Wi-Fi ke router yang bersangkutan.
- Beberapa router memakai HTTPS atau alamat admin khusus; alamat dapat diedit manual.
- Aplikasi tidak memecahkan password, tidak melewati autentikasi, dan tidak mengubah pengaturan router.
- Proyek ini adalah source project Android Studio, bukan APK yang sudah ditandatangani.


## Build APK di GitHub
1. Buat repository baru di GitHub, misalnya `RouterAccess`.
2. Upload seluruh isi folder proyek ini ke repository (termasuk folder `.github`).
3. Pastikan branch utamanya bernama `main`.
4. Buka tab **Actions** di repository. Workflow **Build Router Access APK** akan berjalan otomatis setelah push.
5. Setelah selesai, buka run terbaru, cari bagian **Artifacts**, lalu unduh `RouterAccess-debug-apk`.
6. Ekstrak ZIP artifact untuk mendapatkan `app-debug.apk`.

Workflow ini menghasilkan APK debug untuk pengujian/pemasangan pribadi, bukan APK release bertanda tangan untuk distribusi publik.
