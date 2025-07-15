# 📚 Glossary - Autowarehouse System

## Technical Terms

### **API (Application Programming Interface)**
Interface yang memungkinkan komunikasi antara aplikasi frontend dan backend melalui HTTP requests.

### **Auction**
Sistem lelang real-time dimana user dapat memasang bid untuk memenangkan produk dengan harga terbaik.

### **Authentication**
Proses verifikasi identitas user melalui login dengan email/password atau OAuth.

### **Authorization**
Proses penentuan hak akses user berdasarkan role (Customer/Admin).

### **Bid**
Penawaran harga yang ditempatkan user pada auction tertentu.

### **Cart (Shopping Cart)**
Keranjang belanja virtual yang menyimpan produk yang akan dibeli user.

### **Checkout**
Proses finalisasi pembelian dari shopping cart menjadi order.

### **DTO (Data Transfer Object)**
Object yang digunakan untuk transfer data antar layer aplikasi.

### **Event-Driven Architecture**
Arsitektur dimana komponen berkomunikasi melalui events menggunakan message broker.

### **FCM (Firebase Cloud Messaging)**
Service Google untuk mengirim push notifications ke aplikasi web dan mobile.

### **JWT (JSON Web Token)**
Token format untuk authentication yang aman dan stateless.

### **Kafka**
Message broker untuk event streaming dan komunikasi asynchronous antar services.

### **Microservices**
Arsitektur aplikasi yang terdiri dari services kecil yang independent.

### **OAuth**
Protocol untuk authorization yang memungkinkan login menggunakan provider eksternal (Google).

### **Order**
Record transaksi yang dibuat setelah customer melakukan checkout.

### **Pagination**
Teknik membagi data besar menjadi halaman-halaman kecil untuk performance.

### **PostgreSQL**
Database relational yang digunakan sebagai primary database.

### **Quarkus**
Java framework untuk membangun aplikasi cloud-native dan microservices.

### **Repository Pattern**
Design pattern untuk abstraksi data access layer.

### **REST API**
Architectural style untuk web services menggunakan HTTP methods.

### **Role-Based Access Control (RBAC)**
Sistem kontrol akses berdasarkan role user (Customer/Admin).

### **WebSocket**
Protocol untuk komunikasi real-time dua arah antara client dan server.

### **Wishlist**
Daftar produk yang disimpan user untuk dibeli di kemudian hari.

## Business Terms

### **Admin**
User dengan role administrator yang dapat mengelola produk, order, dan auction.

### **Auction Status**
- **SCHEDULED**: Auction belum dimulai
- **ACTIVE**: Auction sedang berlangsung
- **ENDED**: Auction sudah selesai
- **CANCELLED**: Auction dibatalkan

### **Bid Increment**
Minimum kenaikan harga untuk bid selanjutnya dalam auction.

### **Customer**
User dengan role customer yang dapat berbelanja dan mengikuti auction.

### **Order Status**
- **PENDING**: Order baru dibuat, menunggu konfirmasi
- **PROCESSING**: Order sedang diproses
- **SHIPPED**: Order sudah dikirim
- **DELIVERED**: Order sudah sampai ke customer
- **CANCELLED**: Order dibatalkan

### **Product Categories**
Kategori produk komputer seperti VGA, CPU, RAM, Motherboard, dll.

### **Review**
Ulasan dan rating yang diberikan customer setelah membeli produk.

### **Stock**
Jumlah ketersediaan produk di warehouse.

## UI/UX Terms

### **Breadcrumb**
Navigasi yang menunjukkan lokasi user dalam hierarki website.

### **Card**
Komponen UI untuk menampilkan informasi dalam format kotak.

### **Carousel**
Komponen UI untuk menampilkan multiple images dalam slideshow.

### **Modal**
Pop-up window untuk menampilkan form atau informasi tambahan.

### **Responsive Design**
Design yang dapat menyesuaikan dengan berbagai ukuran layar.

### **Stepper**
Komponen UI untuk menampilkan progress dalam bentuk langkah-langkah.

### **Toast Notification**
Notifikasi pop-up sementara yang muncul di layar.

## System Terms

### **Caching**
Teknik menyimpan data sementara untuk meningkatkan performance.

### **CORS (Cross-Origin Resource Sharing)**
Mechanism untuk mengizinkan request dari domain yang berbeda.

### **Database Index**
Struktur data untuk mempercepat query database.

### **Environment**
- **Development**: Environment untuk development
- **Staging**: Environment untuk testing
- **Production**: Environment untuk live application

### **Load Balancer**
Sistem untuk mendistribusikan traffic ke multiple server instances.

### **Rate Limiting**
Pembatasan jumlah request per unit waktu untuk mencegah abuse.

### **Session**
Data sementara yang disimpan untuk user yang sedang login.

### **SSL/TLS**
Protocol untuk enkripsi komunikasi antara client dan server.

## Firebase Terms

### **Firebase Storage**
Cloud storage service untuk menyimpan file seperti gambar produk.

### **Firebase Authentication**
Service untuk mengelola user authentication dan OAuth.

### **Firestore**
NoSQL database dari Firebase (tidak digunakan dalam project ini).

## Development Terms

### **API Endpoint**
URL specific untuk mengakses resource tertentu melalui API.

### **Build Process**
Proses kompilasi dan packaging aplikasi untuk deployment.

### **CI/CD (Continuous Integration/Continuous Deployment)**
Automated process untuk testing dan deployment aplikasi.

### **Code Review**
Proses review kode oleh developer lain sebelum merge.

### **Git**
Version control system untuk mengelola source code.

### **Linting**
Proses analisis kode untuk menemukan error dan enforce coding standards.

### **Migration**
Script untuk mengubah struktur database.

### **Testing**
- **Unit Testing**: Testing individual components
- **Integration Testing**: Testing interaction antar components
- **E2E Testing**: Testing complete user workflows

### **TypeScript**
Superset JavaScript dengan static typing.

### **Vue 3**
JavaScript framework untuk membangun user interfaces.

## Performance Terms

### **CDN (Content Delivery Network)**
Network server untuk deliver static assets dengan cepat.

### **Lazy Loading**
Teknik loading content hanya saat dibutuhkan.

### **Minification**
Proses mengurangi ukuran file dengan menghapus whitespace dan comments.

### **Optimization**
Proses meningkatkan performance aplikasi.

### **Scalability**
Kemampuan sistem untuk handle increased load.

## Security Terms

### **CSRF (Cross-Site Request Forgery)**
Attack yang memaksa user melakukan action yang tidak diinginkan.

### **Encryption**
Proses mengubah data menjadi format yang tidak dapat dibaca tanpa key.

### **Hashing**
Proses mengubah data menjadi fixed-length string (untuk password).

### **Input Validation**
Proses memvalidasi data input untuk mencegah malicious data.

### **SQL Injection**
Attack yang memanfaatkan vulnerability dalam SQL queries.

### **XSS (Cross-Site Scripting)**
Attack yang inject malicious scripts ke dalam web pages.
