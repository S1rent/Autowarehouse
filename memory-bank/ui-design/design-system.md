# 🎨 UI Design System - Autowarehouse System

## Overview
Comprehensive design system untuk Autowarehouse yang mencakup komponen, layout, dan guidelines berdasarkan UI design files yang tersedia.

## Design Principles
- **Clean & Modern**: Interface yang bersih dan contemporary
- **User-Centric**: Fokus pada user experience dan usability
- **Responsive**: Mobile-first approach dengan desktop optimization
- **Accessible**: WCAG 2.1 AA compliance
- **Consistent**: Unified design language across all pages

## Color Palette

### Primary Colors
```css
--primary-blue: #2563EB      /* Main brand color */
--primary-blue-light: #3B82F6
--primary-blue-dark: #1D4ED8
--primary-blue-50: #EFF6FF

--secondary-gray: #6B7280    /* Secondary actions */
--secondary-gray-light: #9CA3AF
--secondary-gray-dark: #4B5563
```

### Semantic Colors
```css
--success-green: #10B981
--success-green-light: #34D399
--success-green-bg: #ECFDF5

--warning-yellow: #F59E0B
--warning-yellow-light: #FBBF24
--warning-yellow-bg: #FFFBEB

--error-red: #EF4444
--error-red-light: #F87171
--error-red-bg: #FEF2F2

--info-blue: #3B82F6
--info-blue-light: #60A5FA
--info-blue-bg: #EFF6FF
```

### Neutral Colors
```css
--white: #FFFFFF
--gray-50: #F9FAFB
--gray-100: #F3F4F6
--gray-200: #E5E7EB
--gray-300: #D1D5DB
--gray-400: #9CA3AF
--gray-500: #6B7280
--gray-600: #4B5563
--gray-700: #374151
--gray-800: #1F2937
--gray-900: #111827
--black: #000000
```

## Typography

### Font Family
```css
--font-primary: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
--font-mono: 'JetBrains Mono', 'Fira Code', monospace;
```

### Font Sizes
```css
--text-xs: 0.75rem;     /* 12px */
--text-sm: 0.875rem;    /* 14px */
--text-base: 1rem;      /* 16px */
--text-lg: 1.125rem;    /* 18px */
--text-xl: 1.25rem;     /* 20px */
--text-2xl: 1.5rem;     /* 24px */
--text-3xl: 1.875rem;   /* 30px */
--text-4xl: 2.25rem;    /* 36px */
--text-5xl: 3rem;       /* 48px */
```

### Font Weights
```css
--font-light: 300;
--font-normal: 400;
--font-medium: 500;
--font-semibold: 600;
--font-bold: 700;
--font-extrabold: 800;
```

## Spacing System
```css
--space-1: 0.25rem;   /* 4px */
--space-2: 0.5rem;    /* 8px */
--space-3: 0.75rem;   /* 12px */
--space-4: 1rem;      /* 16px */
--space-5: 1.25rem;   /* 20px */
--space-6: 1.5rem;    /* 24px */
--space-8: 2rem;      /* 32px */
--space-10: 2.5rem;   /* 40px */
--space-12: 3rem;     /* 48px */
--space-16: 4rem;     /* 64px */
--space-20: 5rem;     /* 80px */
--space-24: 6rem;     /* 96px */
```

## Border Radius
```css
--radius-sm: 0.125rem;   /* 2px */
--radius-base: 0.25rem;  /* 4px */
--radius-md: 0.375rem;   /* 6px */
--radius-lg: 0.5rem;     /* 8px */
--radius-xl: 0.75rem;    /* 12px */
--radius-2xl: 1rem;      /* 16px */
--radius-full: 9999px;   /* Full rounded */
```

## Shadows
```css
--shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
--shadow-base: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
--shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
--shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
--shadow-xl: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
```

## Component Library

### Buttons
```css
/* Primary Button */
.btn-primary {
  background: var(--primary-blue);
  color: var(--white);
  padding: var(--space-3) var(--space-6);
  border-radius: var(--radius-md);
  font-weight: var(--font-medium);
  transition: all 0.2s ease;
}

.btn-primary:hover {
  background: var(--primary-blue-dark);
  transform: translateY(-1px);
  box-shadow: var(--shadow-md);
}

/* Secondary Button */
.btn-secondary {
  background: var(--white);
  color: var(--primary-blue);
  border: 1px solid var(--primary-blue);
  padding: var(--space-3) var(--space-6);
  border-radius: var(--radius-md);
  font-weight: var(--font-medium);
}

/* Danger Button */
.btn-danger {
  background: var(--error-red);
  color: var(--white);
  padding: var(--space-3) var(--space-6);
  border-radius: var(--radius-md);
  font-weight: var(--font-medium);
}
```

### Form Elements
```css
/* Input Field */
.input-field {
  width: 100%;
  padding: var(--space-3) var(--space-4);
  border: 1px solid var(--gray-300);
  border-radius: var(--radius-md);
  font-size: var(--text-base);
  transition: border-color 0.2s ease;
}

.input-field:focus {
  outline: none;
  border-color: var(--primary-blue);
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

/* Select Dropdown */
.select-field {
  width: 100%;
  padding: var(--space-3) var(--space-4);
  border: 1px solid var(--gray-300);
  border-radius: var(--radius-md);
  background: var(--white);
  font-size: var(--text-base);
}
```

### Cards
```css
.card {
  background: var(--white);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-base);
  padding: var(--space-6);
  transition: box-shadow 0.2s ease;
}

.card:hover {
  box-shadow: var(--shadow-md);
}

.card-header {
  border-bottom: 1px solid var(--gray-200);
  padding-bottom: var(--space-4);
  margin-bottom: var(--space-4);
}
```

## Page Layouts

### Customer Pages Layout
Based on UI design files, customer pages follow this structure:

#### Header Navigation
- Logo (top-left)
- Search bar (center)
- User menu, cart icon, notifications (top-right)
- Category navigation (below header)

#### Main Content Area
- Breadcrumb navigation
- Page title and filters
- Content grid/list
- Pagination

#### Footer
- Company information
- Quick links
- Social media links

### Admin Pages Layout
Admin pages use a sidebar + main content layout:

#### Sidebar Navigation
- Admin logo
- Navigation menu with icons
- User profile section
- Logout option

#### Main Content
- Page header with title and actions
- Content area with tables/forms
- Modals for create/edit operations

## Component Specifications

### Product Card
```html
<div class="product-card">
  <div class="product-image">
    <img src="product.jpg" alt="Product Name">
    <div class="product-badges">
      <span class="badge-auction">Live Auction</span>
    </div>
  </div>
  <div class="product-info">
    <h3 class="product-name">Product Name</h3>
    <div class="product-rating">
      <span class="stars">★★★★☆</span>
      <span class="rating-count">(24)</span>
    </div>
    <div class="product-price">
      <span class="current-price">Rp 25,000,000</span>
      <span class="original-price">Rp 30,000,000</span>
    </div>
    <div class="product-actions">
      <button class="btn-primary">Add to Cart</button>
      <button class="btn-wishlist">♡</button>
    </div>
  </div>
</div>
```

### Auction Timer
```html
<div class="auction-timer">
  <div class="timer-label">Time Remaining</div>
  <div class="timer-display">
    <div class="time-unit">
      <span class="time-value">02</span>
      <span class="time-label">Hours</span>
    </div>
    <div class="time-separator">:</div>
    <div class="time-unit">
      <span class="time-value">45</span>
      <span class="time-label">Minutes</span>
    </div>
    <div class="time-separator">:</div>
    <div class="time-unit">
      <span class="time-value">30</span>
      <span class="time-label">Seconds</span>
    </div>
  </div>
</div>
```

### Order Status Stepper
```html
<div class="order-stepper">
  <div class="step completed">
    <div class="step-icon">✓</div>
    <div class="step-content">
      <div class="step-title">Order Placed</div>
      <div class="step-time">Jan 1, 2025 10:00</div>
    </div>
  </div>
  <div class="step active">
    <div class="step-icon">2</div>
    <div class="step-content">
      <div class="step-title">Processing</div>
      <div class="step-time">In Progress</div>
    </div>
  </div>
  <div class="step pending">
    <div class="step-icon">3</div>
    <div class="step-content">
      <div class="step-title">Shipped</div>
      <div class="step-time">Pending</div>
    </div>
  </div>
</div>
```

## Responsive Breakpoints
```css
/* Mobile First Approach */
@media (min-width: 640px) { /* sm */ }
@media (min-width: 768px) { /* md */ }
@media (min-width: 1024px) { /* lg */ }
@media (min-width: 1280px) { /* xl */ }
@media (min-width: 1536px) { /* 2xl */ }
```

## Animation Guidelines
```css
/* Micro-interactions */
.transition-base {
  transition: all 0.2s ease;
}

.transition-slow {
  transition: all 0.3s ease;
}

/* Hover effects */
.hover-lift:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* Loading animations */
@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.loading-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
```

## Accessibility Guidelines
- Minimum contrast ratio: 4.5:1 for normal text
- Focus indicators visible and consistent
- Keyboard navigation support
- Screen reader friendly markup
- Alt text for all images
- Proper heading hierarchy (h1-h6)

## Dark Mode Support
```css
@media (prefers-color-scheme: dark) {
  :root {
    --bg-primary: var(--gray-900);
    --bg-secondary: var(--gray-800);
    --text-primary: var(--gray-100);
    --text-secondary: var(--gray-300);
    --border-color: var(--gray-700);
  }
}
```

## Icon System
- Use consistent icon library (Heroicons, Feather Icons)
- 16px, 20px, 24px standard sizes
- Stroke width: 1.5px for outline icons
- Fill icons for active states

## Loading States
- Skeleton screens for content loading
- Spinner for actions (buttons, forms)
- Progress bars for file uploads
- Shimmer effect for image loading
