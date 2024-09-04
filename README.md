# Simple Marketplace Project

Proyek ini adalah aplikasi simple-marketplace yang dibangun menggunakan Java Spring Boot.

## Struktur Proyek

Proyek ini menggunakan struktur standar Spring Boot dengan beberapa komponen utama:

- `src/main/java/com/kendimerah/marketplace/`: Direktori utama untuk kode sumber Java
  - `controller/`: Berisi controller untuk menangani request HTTP
  - `service/`: Berisi logika bisnis
  - `entity/`: Berisi model data / entitas
  - `repository/`: Berisi interface untuk akses data
  - `security/`: Berisi konfigurasi dan komponen keamanan
  - `config/`: Berisi konfigurasi aplikasi
  - `dto/`: Berisi objek transfer data

## Fitur Utama

1. Autentikasi dan Otorisasi (JWT)
2. Manajemen Produk
3. Manajemen Keranjang Belanja
4. Pemrosesan Pesanan

## Teknologi yang Digunakan

- Java Spring Boot
- Spring Security
- Spring Data JPA
- JWT untuk autentikasi
- Maven untuk manajemen dependensi

## Konfigurasi

Konfigurasi aplikasi dapat ditemukan di `src/main/resources/application.properties`.

## Menjalankan Aplikasi

1. Pastikan Anda memiliki Java dan Maven terinstal
2. Clone repository ini
3. Navigasi ke direktori proyek
4. Jalankan perintah: `./mvnw spring-boot:run`

## Deployment

Proyek ini dikonfigurasi untuk deployment menggunakan Docker. Lihat `Dockerfile` untuk detailnya.

---

Untuk informasi lebih lanjut, silakan hubungi tim pengembang.