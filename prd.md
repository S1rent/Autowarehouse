# 📄 Product Requirement Document (PRD)

## 1. Product Title
**Autowarehouse System for Computer Store**

---

## 2. Purpose
Autowarehouse adalah sistem terpadu yang dirancang untuk mengelola penjualan, manajemen stok, dan lelang barang di toko komputer. Sistem ini memungkinkan pelanggan melakukan pembelian langsung melalui website, mengikuti lelang produk secara real-time, serta memudahkan admin toko dalam mengatur stok, memproses pesanan, dan mengeksekusi sistem lelang.

---

## 3. Scope
Sistem mencakup katalog produk, shopping cart, checkout, wishlist, ulasan produk, serta sistem lelang langsung berbasis timer. Admin memiliki kontrol penuh terhadap produk, stok, dan proses lelang. Sistem ini dibangun dengan arsitektur modern berbasis microservices dan menggunakan teknologi real-time untuk auction dan notifikasi.

---

## 4. Target Users
- Customer (pembeli produk komputer dan aksesoris)
- Admin toko (pengelola produk dan sistem)
- Distributor (pengisi stok – pengembangan tahap berikutnya)

---

## 5. Features and Requirements

### 5.1. Product Catalog
- Browse produk berdasarkan kategori (VGA, CPU, RAM, dll)
- Fitur pencarian dan filter produk
- Lihat detail produk: gambar, spesifikasi, harga, dan rating
- Gambar disimpan di Firebase Storage

### 5.2. Shopping Cart & Wishlist
- Tambah/hapus produk ke shopping cart
- Update kuantitas dalam cart
- Wishlist: simpan produk favorit
- Checkout dari cart dengan memilih metode pengiriman dan pembayaran

### 5.3. Order Management
- Proses checkout:
  - Validasi stok
  - Perhitungan total harga
  - Buat order record
- Pelanggan dapat melihat status pemesanan:
  - Pending → Diproses → Dikirim → Selesai
- Admin memproses order dan update status

### 5.4. Live Auction System
- Lelang dengan timer real-time (countdown)
- Fitur bidding dinamis menggunakan WebSocket
- Harga awal dan kelipatan ditentukan admin
- Pemenang bidding otomatis masuk ke proses checkout
- Admin dapat memulai dan mengakhiri auction
- Riwayat penawaran ditampilkan secara live

### 5.5. Product Management (Admin)
- Tambah/edit/hapus produk
- Update harga dan stok
- Upload gambar produk (Firebase)
- Set produk untuk dilelang atau dijual langsung

### 5.6. Review & Rating
- Customer dapat memberikan review setelah pembelian
- Rating berupa bintang + komentar
- Review muncul di halaman produk
- Moderasi review oleh admin (opsional)

### 5.7. Notifications
- Notifikasi real-time:
  - Lelang dimulai/selesai
  - Anda menang/kalah lelang
  - Status pesanan berubah
- Implementasi via Firebase Cloud Messaging (FCM)

### 5.8. User Roles & Access Control
- Customer:
  - Registrasi/login
  - Lihat produk, ikut lelang, beli produk
- Admin:
  - Kelola produk, stok, order, lelang, kupon diskon
  - Akses laporan dan histori order

### 5.9. Event-Driven Processing (Kafka)
- `OrderPlacedEvent`: dipicu saat checkout selesai
- `StockUpdatedEvent`: update stok oleh admin
- `AuctionEndedEvent`: lelang selesai → kirim hasil
- `ProductUpdatedEvent`: sinkronisasi data frontend/backend

---

## 6. Technical Requirements

### 6.1. Frontend
- Vue 3 + TypeScript
- Composition API
- Pinia (state management)
- WebSocket client untuk auction
- Tolong buat language localization untuk indonesia dan inggris.

### 6.2. Backend
- Java + Quarkus
- REST API + WebSocket endpoint
- Repository pattern + DTO + Validation
- Integrasi dengan Kafka

### 6.3. Database
- PostgreSQL
- Tabel utama: products, users, orders, auctions, bids, reviews

### 6.4. Storage
- Firebase Storage untuk gambar produk
- FCM untuk push notification

---

## 7. Non-Functional Requirements

### 7.1. Performance
- Websocket response latency < 300ms
- Page load time < 2s
- Support 1,000 concurrent bidding users
- Please perform caching as well

### 7.2. Scalability
- Microservice-compatible backend
- Horizontal scalability via Kubernetes (optional)

### 7.3. Security
- JWT-based authentication
- Role-based access
- Input validation dan sanitasi

### 7.4. Usability
- UI/UX clean, responsive dan mobile-friendly
- Dark mode opsional
- Multi-language ready (fase selanjutnya)

### 7.5. Availability
- Uptime > 99.5%
- Backup harian database dan storage

---

## 8. Assumptions
- User memiliki akses internet yang stabil untuk mengikuti auction
- Sistem payment gateway akan diintegrasikan pada fase berikutnya
- Semua admin memiliki basic knowledge dalam manajemen stok

---

## 9. Constraints
- Auction tidak bisa di-pause
- Produk hanya bisa dijual melalui satu mode: lelang **atau** normal
- Belum ada integrasi dengan ekspedisi atau logistik eksternal

---

## 10. Risks
- Koneksi buruk menyebabkan delay pada auction
- Abuse terhadap fitur bidding (spam bid) jika tanpa autentikasi kuat
- Potensi perbedaan stok karena race condition (butuh sinkronisasi yang kuat)

---

# 📌 Appendix

**Definisi Penting:**
- **Live Auction:** Lelang real-time yang bisa diikuti user untuk menawar barang tertentu dalam waktu terbatas.
- **Order:** Catatan transaksi yang dibuat saat customer checkout.
- **Wishlist:** Daftar produk yang ditandai user untuk disimpan dan dibeli nanti.
- **WebSocket:** Protokol komunikasi real-time dua arah yang digunakan untuk lelang.
- **FCM:** Firebase Cloud Messaging, digunakan untuk kirim notifikasi.

---
