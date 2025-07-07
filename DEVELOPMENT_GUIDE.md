# 🚀 Autowarehouse Development Guide

## 📋 Prerequisites

Sebelum menjalankan aplikasi, pastikan Anda telah menginstall:

### Required Software
1. **Node.js** (v18 atau lebih tinggi)
   - Download dari: https://nodejs.org/
   - Verifikasi: `node --version`

2. **Java** (v17 atau lebih tinggi)
   - Download dari: https://adoptium.net/
   - Verifikasi: `java -version`

3. **Apache Maven** (v3.8 atau lebih tinggi)
   - Download dari: https://maven.apache.org/
   - Verifikasi: `mvn --version`

4. **PostgreSQL** (v13 atau lebih tinggi) - Optional untuk production
   - Download dari: https://www.postgresql.org/
   - Untuk development, Quarkus akan menggunakan H2 database

### Optional Tools
- **Git** untuk version control
- **VS Code** dengan extension Vue.js dan Java
- **Postman** untuk testing API

## 🏗️ Project Structure

```
autowarehouse/
├── autowarehouse-frontend/     # Vue.js Frontend
│   ├── src/
│   │   ├── components/        # Vue components
│   │   ├── views/            # Page components
│   │   ├── stores/           # Pinia state management
│   │   ├── services/         # API services
│   │   └── router/           # Vue Router configuration
│   ├── package.json
│   └── vite.config.ts
├── autowarehouse-backend/      # Quarkus Backend
│   ├── src/main/java/com/autowarehouse/
│   │   ├── entity/           # JPA Entities
│   │   ├── resource/         # REST API Controllers
│   │   ├── service/          # Business Logic Services
│   │   └── dto/              # Data Transfer Objects
│   ├── pom.xml
│   └── application.properties
├── start-dev.bat              # Windows startup script
├── start-dev.sh               # Linux/Mac startup script
└── DEVELOPMENT_GUIDE.md       # This file
```

## 🚀 Quick Start

### Option 1: Automatic Startup (Recommended)

#### Windows:
```bash
# Double-click atau jalankan dari command prompt
start-dev.bat
```

#### Linux/Mac:
```bash
# Berikan permission execute terlebih dahulu
chmod +x start-dev.sh

# Jalankan script
./start-dev.sh
```

### Option 2: Manual Startup

#### 1. Start Backend (Terminal 1)
```bash
cd autowarehouse-backend
mvn quarkus:dev
```

#### 2. Start Frontend (Terminal 2)
```bash
cd autowarehouse-frontend
npm install  # Hanya perlu dijalankan sekali
npm run dev
```

## 🌐 Access URLs

Setelah kedua server berjalan:

- **Frontend Application**: http://localhost:5173
- **Backend API**: http://localhost:8080
- **API Documentation (Swagger)**: http://localhost:8080/q/swagger-ui
- **H2 Database Console**: http://localhost:8080/h2-console (development only)

## 🔧 Configuration

### Frontend Configuration (.env)
```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_APP_NAME=Autowarehouse
VITE_APP_VERSION=1.0.0
VITE_DEV_MODE=true
```

### Backend Configuration (application.properties)
```properties
# Database
quarkus.datasource.db-kind=h2
quarkus.datasource.jdbc.url=jdbc:h2:mem:testdb
quarkus.hibernate-orm.database.generation=drop-and-create

# CORS
quarkus.http.cors=true
quarkus.http.cors.origins=http://localhost:5173

# Dev Services
quarkus.http.port=8080
```

## 🛠️ Development Workflow

### Frontend Development
```bash
cd autowarehouse-frontend

# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint code
npm run lint

# Type check
npm run type-check
```

### Backend Development
```bash
cd autowarehouse-backend

# Start development server with hot reload
mvn quarkus:dev

# Run tests
mvn test

# Build for production
mvn package

# Build native executable
mvn package -Pnative
```

## 📊 Features Status

### ✅ Implemented Features
- **Authentication System**: Login, registration, JWT tokens
- **Product Catalog**: Browse, search, filter products
- **Shopping Cart**: Add, remove, update quantities
- **Live Auctions**: View auctions, place bids, watch auctions
- **User Management**: Profile management, order history
- **Admin Panel**: Dashboard, product management, order management
- **Responsive Design**: Mobile-first approach
- **State Management**: Pinia stores for frontend state
- **API Integration**: Complete REST API with Quarkus

### 🚧 In Progress
- **Real-time Features**: WebSocket for live auctions
- **Payment Integration**: Payment gateway integration
- **Email Notifications**: Order confirmations, auction updates
- **File Upload**: Product images, user avatars

### 📋 Planned Features
- **Advanced Search**: Elasticsearch integration
- **Recommendation Engine**: AI-powered product recommendations
- **Analytics Dashboard**: Business intelligence features
- **Mobile App**: React Native mobile application

## 🔍 API Endpoints

### Authentication
- `POST /api/users/login` - User login
- `POST /api/users/register` - User registration
- `POST /api/users/forgot-password` - Password reset

### Products
- `GET /api/products` - Get all products with filters
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/featured/popular` - Get popular products
- `GET /api/products/featured/on-sale` - Get sale products

### Auctions
- `GET /api/auctions` - Get all auctions
- `GET /api/auctions/live` - Get live auctions
- `POST /api/auctions/{id}/bids` - Place bid
- `POST /api/auctions/{id}/watch` - Watch auction

### Shopping Cart
- `GET /api/cart/user/{userId}` - Get user cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/{id}/quantity` - Update quantity
- `DELETE /api/cart/{id}` - Remove item

### Orders
- `POST /api/orders/create` - Create order
- `GET /api/orders/user/{userId}` - Get user orders
- `GET /api/orders/{id}` - Get order details

## 🐛 Troubleshooting

### Common Issues

#### Port Already in Use
```bash
# Kill process using port 8080 (Backend)
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Kill process using port 5173 (Frontend)
netstat -ano | findstr :5173
taskkill /PID <PID> /F
```

#### Frontend Build Issues
```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install

# Clear Vite cache
rm -rf node_modules/.vite
```

#### Backend Build Issues
```bash
# Clean Maven cache
mvn clean

# Rebuild project
mvn clean compile
```

#### Database Issues
```bash
# Reset H2 database (development)
# Stop backend server and restart - H2 will recreate tables
```

### Environment Issues

#### Java Version
```bash
# Check Java version
java -version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/java17
```

#### Node.js Version
```bash
# Check Node version
node --version

# Use Node Version Manager if needed
nvm use 18
```

## 📝 Development Tips

### Frontend Development
1. **Hot Reload**: Vite provides instant hot reload for development
2. **TypeScript**: Use TypeScript for better type safety
3. **Vue DevTools**: Install browser extension for debugging
4. **Tailwind CSS**: Use utility classes for styling
5. **Pinia DevTools**: Debug state management

### Backend Development
1. **Quarkus Dev Mode**: Automatic hot reload for Java changes
2. **Swagger UI**: Test APIs directly from browser
3. **H2 Console**: Inspect database during development
4. **Logging**: Use proper logging levels for debugging
5. **Testing**: Write unit tests for services and resources

### Code Quality
1. **ESLint**: Frontend code linting
2. **Prettier**: Code formatting
3. **TypeScript**: Type checking
4. **Maven Checkstyle**: Backend code style checking
5. **Git Hooks**: Pre-commit hooks for quality checks

## 🚀 Deployment

### Frontend Deployment
```bash
# Build for production
npm run build

# Deploy to static hosting (Vercel, Netlify, etc.)
# Upload dist/ folder contents
```

### Backend Deployment
```bash
# Build JAR file
mvn package

# Run JAR file
java -jar target/quarkus-app/quarkus-run.jar

# Or build native executable
mvn package -Pnative
./target/autowarehouse-backend-1.0.0-SNAPSHOT-runner
```

## 📞 Support

Jika mengalami masalah:

1. **Check Prerequisites**: Pastikan semua software terinstall dengan benar
2. **Check Ports**: Pastikan port 8080 dan 5173 tidak digunakan aplikasi lain
3. **Check Logs**: Lihat console output untuk error messages
4. **Restart Services**: Stop dan start ulang kedua server
5. **Clear Cache**: Clear browser cache dan restart browser

## 🎯 Next Steps

1. **Setup Database**: Configure PostgreSQL for production
2. **Add Authentication**: Implement JWT token validation
3. **Add Real-time Features**: WebSocket for live auctions
4. **Add Testing**: Unit tests dan integration tests
5. **Add CI/CD**: Automated deployment pipeline

---

Happy coding! 🎉
