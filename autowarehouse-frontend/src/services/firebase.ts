import { initializeApp } from 'firebase/app'
import { getStorage, ref, uploadBytes, getDownloadURL, deleteObject } from 'firebase/storage'

// Firebase configuration
// TODO: Replace with your actual Firebase config
const firebaseConfig = {
  apiKey: "your-api-key",
  authDomain: "your-project.firebaseapp.com",
  projectId: "your-project-id",
  storageBucket: "your-project.appspot.com",
  messagingSenderId: "123456789",
  appId: "your-app-id"
}

// Initialize Firebase
const app = initializeApp(firebaseConfig)

// Initialize Firebase Storage
const storage = getStorage(app)

// Upload progress callback type
export interface UploadProgress {
  bytesTransferred: number
  totalBytes: number
  percentage: number
}

// Upload result type
export interface UploadResult {
  url: string
  path: string
  name: string
}

// Generate unique filename with timestamp and random string
const generateFileName = (originalName: string): string => {
  const timestamp = Date.now()
  const randomString = Math.random().toString(36).substring(2, 15)
  const extension = originalName.split('.').pop()
  return `categories/${timestamp}_${randomString}.${extension}`
}

// Upload image to Firebase Storage
export const uploadCategoryImage = async (
  file: File,
  onProgress?: (progress: UploadProgress) => void
): Promise<UploadResult> => {
  try {
    // Validate file
    if (!file.type.startsWith('image/')) {
      throw new Error('File harus berupa gambar (PNG, JPG, JPEG)')
    }

    if (file.size > 2 * 1024 * 1024) {
      throw new Error('Ukuran file terlalu besar. Maksimal 2MB.')
    }

    // Generate unique filename
    const fileName = generateFileName(file.name)
    const storageRef = ref(storage, fileName)

    // Upload file
    const snapshot = await uploadBytes(storageRef, file)
    
    // Get download URL
    const downloadURL = await getDownloadURL(snapshot.ref)

    return {
      url: downloadURL,
      path: fileName,
      name: file.name
    }
  } catch (error) {
    console.error('Error uploading image:', error)
    throw error
  }
}

// Delete image from Firebase Storage
export const deleteCategoryImage = async (imagePath: string): Promise<void> => {
  try {
    if (!imagePath) return
    
    // Extract path from URL if it's a full URL
    let path = imagePath
    if (imagePath.includes('firebase')) {
      // Extract path from Firebase URL
      const url = new URL(imagePath)
      const pathMatch = url.pathname.match(/\/o\/(.+)\?/)
      if (pathMatch) {
        path = decodeURIComponent(pathMatch[1])
      }
    }

    const storageRef = ref(storage, path)
    await deleteObject(storageRef)
  } catch (error) {
    console.error('Error deleting image:', error)
    // Don't throw error for delete operations to avoid blocking other operations
  }
}

// Validate image file
export const validateImageFile = (file: File): string | null => {
  if (!file.type.startsWith('image/')) {
    return 'File harus berupa gambar (PNG, JPG, JPEG)'
  }

  if (file.size > 2 * 1024 * 1024) {
    return 'Ukuran file terlalu besar. Maksimal 2MB.'
  }

  return null
}

export { storage }
