````markdown
# Firebase Storage Setup Guide

This guide will help you set up Firebase Storage for the Admin Category Management image upload functionality.

## Prerequisites

- Firebase project created at [Firebase Console](https://console.firebase.google.com/)
- Firebase Storage enabled in your project

## Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Create a project" or select existing project
3. Follow the setup wizard

## Step 2: Enable Firebase Storage

1. In your Firebase project console, go to "Storage" in the left sidebar
2. Click "Get started"
3. Choose your security rules (start in test mode for development)
4. Select a storage location (choose closest to your users)

## Step 3: Get Firebase Configuration

1. In Firebase Console, go to Project Settings (gear icon)
2. Scroll down to "Your apps" section
3. Click "Add app" and select Web (</>) if you haven't already
4. Register your app with a nickname
5. Copy the Firebase configuration object

## Step 4: Update Firebase Configuration

Replace the placeholder configuration in `src/services/firebase.ts`:

```typescript
const firebaseConfig = {
  apiKey: "AIzaSyBq6004V8DsHp69iG5ljSJbQ0-hc9jhCh4",
  authDomain: "autowarehouse-c3947.firebaseapp.com",
  projectId: "autowarehouse-c3947",
  storageBucket: "autowarehouse-c3947.firebasestorage.app",
  messagingSenderId: "191069017894",
  appId: "1:191069017894:web:783ac1cffdab46995ed3e7",
  measurementId: "G-ZWZH3PT1QG"
};
````

## Step 5: Configure Storage Security Rules

In Firebase Console > Storage > Rules, update the rules for development:

```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    // Allow read/write access to category images
    match /categories/{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

For production, use more restrictive rules:

```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    // Allow authenticated users to upload category images
    match /categories/{allPaths=**} {
      allow read: if true; // Public read access for category images
      allow write: if request.auth != null 
        && request.auth.token.role == 'admin' // Only admins can upload
        && resource.size < 2 * 1024 * 1024; // Max 2MB
    }
  }
}
```

## Step 6: Environment Variables (Optional)

You can also use environment variables for Firebase config:

Create `.env` file in the frontend root:

```env
VITE_FIREBASE_API_KEY=your-api-key
VITE_FIREBASE_AUTH_DOMAIN=your-project.firebaseapp.com
VITE_FIREBASE_PROJECT_ID=your-project-id
VITE_FIREBASE_STORAGE_BUCKET=your-project.appspot.com
VITE_FIREBASE_MESSAGING_SENDER_ID=123456789012
VITE_FIREBASE_APP_ID=1:123456789012:web:abcdef123456
```

Then update `firebase.ts`:

```typescript
const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID
}
```

## Features Implemented

✅ __Image Upload__: Upload category images to Firebase Storage ✅ __File Validation__: Size (2MB max) and type (images only) validation ✅ __Upload Progress__: Real-time upload progress indicator ✅ __Error Handling__: Comprehensive error handling and user feedback ✅ __Image Preview__: Instant preview of selected images ✅ __Auto File Naming__: Unique file names with timestamp and random string ✅ __Image Cleanup__: Automatic deletion of old images when updating/deleting categories ✅ __Fallback UI__: Beautiful fallback when no image is uploaded

## File Structure

```javascript
src/
├── services/
│   └── firebase.ts          # Firebase configuration and upload functions
└── views/admin/
    └── AdminCategoryView.vue # Category management with image upload
```

## Usage

1. Admin opens Category Management
2. Clicks "Add Category" or edits existing category
3. Clicks "Pilih Gambar" to select image file
4. File is validated and uploaded to Firebase Storage
5. Progress bar shows upload status
6. Image URL is saved to backend with category data
7. Images are displayed in category cards

## Troubleshooting

### Upload Fails

- Check Firebase configuration
- Verify Storage is enabled
- Check security rules
- Ensure file size is under 2MB

### Images Don't Display

- Check if imageUrl is properly saved
- Verify Firebase Storage public access rules
- Check browser console for CORS errors

### Permission Denied

- Update Firebase Storage security rules
- Ensure user authentication if required
