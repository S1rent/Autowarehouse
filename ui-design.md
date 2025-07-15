🧑‍💻 CUSTOMER PAGES
1. Landing Page / Homepage
Tujuan: Menyambut user dengan promosi, highlight produk, dan akses cepat ke kategori.

Komponen:

Banner promo

Produk unggulan / terbaru

Kategori produk

Tombol login / register

Tombol akses ke Live Auction (jika sedang berlangsung)

2. Product Listing Page
Tujuan: Menampilkan daftar produk berdasarkan kategori.

Komponen:

Filter (kategori, harga, rating, stok, brand)

Sortir (termurah, terbaru, terpopuler)

List card produk (gambar, nama, harga, tombol lihat detail)

Interaksi:

Tambahkan ke cart

Tambahkan ke wishlist

Navigasi ke halaman detail produk

3. Product Detail Page
Tujuan: Menampilkan detail lengkap dari sebuah produk.

Komponen:

Gambar produk (carousel)

Spesifikasi teknis

Harga dan stok

Tombol tambah ke cart / wishlist

Tombol “Ikuti Lelang” (jika sedang dilelang)

Review dan rating dari user lain

Interaksi:

Add to cart / wishlist

Submit review (jika user sudah beli)

4. Shopping Cart Page
Tujuan: Menampilkan produk yang akan dibeli.

Komponen:

Daftar item dalam cart (nama, kuantitas, harga)

Total harga

Tombol “Checkout”

Opsi edit kuantitas / hapus item

5. Checkout Page
Tujuan: Menyelesaikan pembelian.

Komponen:

Ringkasan produk

Input alamat pengiriman

Opsi pengiriman (instant, reguler, dll)

Tombol “Place Order”

6. Order History Page
Tujuan: Melihat riwayat dan status pesanan.

Komponen:

Tabel order: no. pesanan, tanggal, status, total

Status order: Pending → Diproses → Dikirim → Selesai

Tombol “Lihat Detail”

7. Order Detail Page
Tujuan: Menampilkan detail satu pesanan.

Komponen:

Produk yang dibeli

Status pengiriman (stepper UI)

Info pembayaran

Tombol “Tulis Review” (jika selesai)

8. Wishlist Page
Tujuan: Melihat dan mengelola wishlist.

Komponen:

List produk yang disimpan

Tombol pindahkan ke cart / hapus

9. Live Auction Page
Tujuan: Menampilkan lelang yang sedang berlangsung.

Komponen:

Countdown timer

Gambar dan detail produk yang dilelang

Bid history (realtime)

Form untuk submit penawaran

Harga saat ini

Pemenang terakhir (jika sudah selesai)

Interaksi:

Submit bid

Lihat histori penawaran

10. User Profile Page
Tujuan: Mengelola informasi user.

Komponen:

Data pribadi (nama, nomor telepon, email, alamat)

Ubah password

Logout

🧑‍🏭 ADMIN PAGES
1. Admin Dashboard
Tujuan: Ringkasan sistem dan statistik.

Komponen:

Total order, produk, stok rendah, lelang aktif

Grafik penjualan

Link ke manajemen produk, lelang, pesanan

2. Product Management Page
Tujuan: CRUD produk.

Komponen:

Tabel produk: nama, kategori, harga, stok, status

Tombol tambah / edit / hapus produk

Upload gambar (Firebase)

Filter dan pencarian

3. Product Form Page (Create/Edit)
Tujuan: Form untuk tambah/edit produk.

Komponen:

Nama produk, kategori, spesifikasi, harga

Upload gambar

Checkbox: tersedia untuk lelang atau tidak

4. Stock Management Page
Tujuan: Lihat dan update stok produk.

Komponen:

Tabel stok

Tombol tambah stok manual

Notifikasi stok rendah

5. Auction Management Page
Tujuan: Kelola sesi lelang.

Komponen:

Tabel lelang: produk, status, waktu mulai/selesai

Tombol mulai lelang, tutup lelang

Akses ke histori bidding

6. Order Management Page
Tujuan: Pantau dan proses order.

Komponen:

Tabel order: customer, status, total, tanggal

Detail order

Tombol ubah status (diproses, dikirim)

7. Review Moderation Page
Tujuan: Lihat dan moderasi ulasan produk.

Komponen:

Tabel review: produk, user, rating, komentar

Tombol “hide”, “delete”

1. User Management Page (optional)
Tujuan: Kelola akun user.

Komponen:

Tabel user: email, role, status

Blokir user, ubah role

⚙️ SYSTEM / SHARED PAGES
1. Login Page
Tujuan: Autentikasi user/admin.

Komponen:

Input email + password

Login sebagai admin / customer
Bisa alternatif login dengan google juga ya
Ada bagian forgot password juga

1.b Forgot Password,
Tujuan: reset password pengguna by email, lalu sistem akan mengirimkan email
berisi link reset password ke email pengguna.

1.c Reset Password,
Reset password pengguna: berdasarkan link yang sudah di klik pengguna yang telah
dikirimkan oleh sistem ke email mereka

2. Register Page
Tujuan: Registrasi customer baru.

Komponen:

Nama lengkap, email, password, konfirmasi password
Bisa alternatif register dengan google juga ya

3. Error Page (404 / 500)
Tujuan: Tampilan error jika halaman tidak ditemukan atau terjadi error server.

4. Notification System (Toast)
Tujuan: Notifikasi real-time untuk:

Lelang dimulai/selesai

Penawaran diterima

Order berhasil

Error/gagal transaksi