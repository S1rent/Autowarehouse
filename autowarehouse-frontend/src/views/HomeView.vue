<template>
  <div class="home-view">
    <div class="container">
      <header class="header">
        <div class="nav-container">
          <div class="logo">
            <h1>Autowarehouse</h1>
            <span class="tagline">Computer E-commerce & Live Auction</span>
          </div>
          <nav class="nav-links">
            <router-link to="/login" class="btn btn-secondary">Login</router-link>
            <router-link to="/register" class="btn btn-primary">Register</router-link>
          </nav>
        </div>
      </header>

      <main class="main-content">
        <div class="hero-section">
          <h2>Welcome to Autowarehouse</h2>
          <p class="hero-description">
            Your ultimate destination for computer hardware with live auction features
          </p>
          <div class="hero-actions">
            <router-link to="/register" class="btn btn-primary">Get Started</router-link>
            <router-link to="/login" class="btn btn-secondary">Sign In</router-link>
          </div>
        </div>

        <div class="features-preview">
          <div class="feature-card">
            <h3>🛒 E-commerce</h3>
            <p>Browse and purchase computer hardware</p>
          </div>
          <div class="feature-card">
            <h3>⚡ Live Auction</h3>
            <p>Participate in real-time bidding</p>
          </div>
          <div class="feature-card">
            <h3>🔧 Quality Products</h3>
            <p>Premium computer components</p>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
// Empty home page - will be expanded later
</script>

<style scoped>
.home-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.header {
  padding: 2rem 0;
}

.nav-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 1rem;
}

.logo h1 {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 0.25rem;
}

.tagline {
  font-size: 0.875rem;
  opacity: 0.9;
}

.nav-links {
  display: flex;
  gap: 1rem;
}

.main-content {
  padding: 4rem 0;
  text-align: center;
}

.hero-section {
  margin-bottom: 4rem;
}

.hero-section h2 {
  font-size: 3rem;
  font-weight: 700;
  margin-bottom: 1rem;
}

.hero-description {
  font-size: 1.25rem;
  opacity: 0.9;
  margin-bottom: 2rem;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.hero-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.features-preview {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.feature-card {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 1rem;
  padding: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.feature-card h3 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.feature-card p {
  opacity: 0.9;
}

@media (max-width: 768px) {
  .nav-container {
    flex-direction: column;
    text-align: center;
  }
  
  .hero-section h2 {
    font-size: 2rem;
  }
  
  .hero-actions {
    flex-direction: column;
    align-items: center;
  }
  
  .hero-actions .btn {
    width: 200px;
  }
}
</style>
