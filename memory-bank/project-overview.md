# 📋 Project Overview - Autowarehouse System

## Project Title
**Autowarehouse System for Computer Store**

## Purpose
Autowarehouse adalah sistem terpadu yang dirancang untuk mengelola penjualan, manajemen stok, dan lelang barang di toko komputer. Sistem ini memungkinkan pelanggan melakukan pembelian langsung melalui website, mengikuti lelang produk secara real-time, serta memudahkan admin toko dalam mengatur stok, memproses pesanan, dan mengeksekusi sistem lelang.

## Scope
Sistem mencakup katalog produk, shopping cart, checkout, wishlist, ulasan produk, serta sistem lelang langsung berbasis timer. Admin memiliki kontrol penuh terhadap produk, stok, dan proses lelang. Sistem ini dibangun dengan arsitektur modern berbasis microservices dan menggunakan teknologi real-time untuk auction dan notifikasi.

## Target Users
- **Customer**: Pembeli produk komputer dan aksesoris
- **Admin Toko**: Pengelola produk dan sistem
- **Distributor**: Pengisi stok (pengembangan tahap berikutnya)

## Key Features
1. **Product Catalog** - Browse, search, filter produk komputer
2. **Shopping Cart & Wishlist** - Manajemen keranjang belanja
3. **Order Management** - Proses checkout dan tracking pesanan
4. **Live Auction System** - Lelang real-time dengan WebSocket
5. **Product Management** - Admin CRUD produk dan stok
6. **Review & Rating** - Sistem ulasan pelanggan
7. **Notifications** - Real-time notifications via FCM
8. **User Roles & Access Control** - Customer dan Admin roles

## Business Goals
- Meningkatkan penjualan melalui sistem lelang yang menarik
- Otomatisasi manajemen stok dan pesanan
- Memberikan pengalaman berbelanja yang modern dan real-time
- Memudahkan admin dalam mengelola toko online

## Success Metrics
- Support 1,000 concurrent bidding users
- WebSocket response latency < 300ms
- Page load time < 2s
- Uptime > 99.5%
