# Autowarehouse - Computer E-commerce & Live Auction Platform

Autowarehouse adalah platform e-commerce komputer dengan fitur live auction yang dibangun menggunakan teknologi modern untuk memberikan pengalaman berbelanja yang optimal.

## 🚀 Features

### Core Features
- **E-commerce Platform**: Jual beli komputer dan komponen
- **Live Auction System**: Sistem lelang real-time
- **User Authentication**: Login/Register dengan validasi lengkap
- **Responsive Design**: Optimized untuk semua device
- **Modern UI/UX**: Design yang clean dan user-friendly

### Authentication Features
- ✅ User Registration dengan validasi lengkap
- ✅ User Login dengan remember me
- ✅ Password strength indicator
- ✅ Google OAuth integration (UI ready)
- ✅ Form validation dan error handling
- ✅ Loading states dan smooth transitions

## 🛠 Tech Stack

### Frontend
- **Vue 3** - Progressive JavaScript framework
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool
- **Vue Router 4** - Client-side routing
- **CSS3** - Modern styling dengan CSS Variables

### Development Tools
- **ESLint** - Code linting
- **Prettier** - Code formatting
- **TypeScript** - Static type checking

## 📁 Project Structure

```
Autowarehouse/
├── memory-bank/                    # Project documentation
│   ├── project-overview.md
│   ├── technical-architecture.md
│   ├── ui-ux-analysis.md
│   └── frontend-implementation.md
├── autowarehouse-frontend/         # Vue.js frontend application
│   ├── src/
│   │   ├── views/                  # Page components
│   │   ├── router/                 # Routing configuration
│   │   ├── App.vue                 # Root component
│   │   ├── main.ts                 # Application entry point
│   │   └── style.css               # Global styles
│   ├── public/                     # Static assets
│   ├── package.json
│   └── vite.config.ts
├── ui-design/                      # UI/UX design files
├── prd.md                          # Product Requirements Document
└── README.md
```

## 🚦 Getting Started

### Prerequisites
- Node.js 18+ 
- npm atau yarn

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Autowarehouse
   ```

2. **Install frontend dependencies**
   ```bash
   cd autowarehouse-frontend
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

4. **Open browser**
   ```
   http://localhost:3000
   ```

### Available Scripts

```bash
# Development
npm run dev          # Start development server
npm run build        # Build for production
npm run preview      # Preview production build

# Code Quality
npm run type-check   # TypeScript type checking
npm run lint         # ESLint code linting
```

## 📱 Pages & Features

### 🏠 Home Page (`/`)
- Hero section dengan branding
- Feature preview cards
- Navigation ke login/register
- Responsive design

### 🔐 Authentication

#### Login Page (`/login`)
- Email & password validation
- Password visibility toggle
- Remember me functionality
- Google OAuth integration (UI)
- Loading states
- Error handling

#### Register Page (`/register`)
- Multi-step form validation
- Real-time password strength indicator
- Password confirmation
- Terms & conditions agreement
- Newsletter subscription option
- Google OAuth integration (UI)

## 🎨 Design System

### Color Palette
- **Primary**: #2563eb (Blue)
- **Secondary**: #64748b (Slate)
- **Success**: #10b981 (Green)
- **Warning**: #f59e0b (Amber)
- **Error**: #ef4444 (Red)

### Typography
- **Font**: Inter (Google Fonts)
- **Weights**: 300, 400, 500, 600, 700

### Components
- Modern button styles dengan hover effects
- Consistent form styling
- Card components dengan shadows
- Loading spinners
- Smooth transitions

## 📋 Development Status

### ✅ Completed
- [x] Project setup dan configuration
- [x] Frontend architecture dengan Vue 3 + TypeScript
- [x] Routing system
- [x] Authentication UI (Login/Register)
- [x] Form validation
- [x] Password strength indicator
- [x] Responsive design
- [x] Loading states
- [x] Error handling
- [x] Design system implementation

### 🚧 In Progress
- [ ] Backend API development
- [ ] Database design
- [ ] Authentication backend integration

### 📅 Planned
- [ ] Product catalog
- [ ] Shopping cart
- [ ] Live auction system
- [ ] Payment integration
- [ ] User dashboard
- [ ] Admin panel
- [ ] Real-time notifications
- [ ] Testing suite
- [ ] Deployment setup

## 🔧 Configuration

### Environment Variables
```env
# Development
VITE_API_URL=http://localhost:8000/api
VITE_GOOGLE_CLIENT_ID=your_google_client_id

# Production
VITE_API_URL=https://api.autowarehouse.com
VITE_GOOGLE_CLIENT_ID=your_production_google_client_id
```

## 🧪 Testing

Testing framework akan diimplementasikan menggunakan:
- **Vitest** - Unit testing
- **Vue Test Utils** - Component testing
- **Cypress** - E2E testing

## 📦 Deployment

### Frontend Deployment Options
- **Vercel** (Recommended)
- **Netlify**
- **AWS S3 + CloudFront**
- **Firebase Hosting**

### Build Commands
```bash
npm run build        # Build for production
npm run preview      # Preview production build locally
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 Documentation

Dokumentasi lengkap tersedia di folder `memory-bank/`:
- **project-overview.md** - Overview proyek dan requirements
- **technical-architecture.md** - Arsitektur teknis
- **ui-ux-analysis.md** - Analisis UI/UX design
- **frontend-implementation.md** - Detail implementasi frontend

## 📞 Support

Untuk pertanyaan atau dukungan, silakan buat issue di repository ini.

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Autowarehouse** - Your ultimate destination for computer hardware with live auction features 🖥️⚡
