import { initializeApp } from 'firebase/app'
import { getStorage, ref, uploadBytes, getDownloadURL, deleteObject } from 'firebase/storage'

// Firebase configuration
// TODO: Replace with your actual Firebase config
const firebaseConfig = {
  apiKey: "AIzaSyBq6004V8DsHp69iG5ljSJbQ0-hc9jhCh4",
  authDomain: "autowarehouse-c3947.firebaseapp.com",
  projectId: "autowarehouse-c3947",
  storageBucket: "autowarehouse-c3947.firebasestorage.app",
  messagingSenderId: "191069017894",
  appId: "1:191069017894:web:783ac1cffdab46995ed3e7",
  measurementId: "G-ZWZH3PT1QG"
};

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
const generateFileName = (originalName: string, folder: string = 'categories'): string => {
  const timestamp = Date.now()
  const randomString = Math.random().toString(36).substring(2, 15)
  const extension = originalName.split('.').pop()
  return `${folder}/${timestamp}_${randomString}.${extension}`
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

// Upload product image to Firebase Storage
export const uploadProductImage = async (
  file: File,
  onProgress?: (progress: UploadProgress) => void
): Promise<UploadResult> => {
  try {
    // Validate file
    if (!file.type.startsWith('image/')) {
      throw new Error('File harus berupa gambar (PNG, JPG, JPEG)')
    }

    if (file.size > 5 * 1024 * 1024) { // 5MB limit for product images
      throw new Error('Ukuran file terlalu besar. Maksimal 5MB.')
    }

    // Generate unique filename for products folder
    const fileName = generateFileName(file.name, 'products')
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
    console.error('Error uploading product image:', error)
    throw error
  }
}

// Upload multiple product images
export const uploadProductImages = async (
  files: File[],
  onProgress?: (fileIndex: number, progress: UploadProgress) => void
): Promise<UploadResult[]> => {
  const results: UploadResult[] = []
  
  for (let i = 0; i < files.length; i++) {
    const file = files[i]
    try {
      const result = await uploadProductImage(file, (progress) => {
        if (onProgress) {
          onProgress(i, progress)
        }
      })
      results.push(result)
    } catch (error) {
      console.error(`Error uploading file ${file.name}:`, error)
      throw new Error(`Failed to upload ${file.name}: ${error}`)
    }
  }
  
  return results
}

// Delete product image from Firebase Storage
export const deleteProductImage = async (imagePath: string): Promise<void> => {
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
    console.error('Error deleting product image:', error)
    // Don't throw error for delete operations to avoid blocking other operations
  }
}

// Delete multiple product images
export const deleteProductImages = async (imagePaths: string[]): Promise<void> => {
  const deletePromises = imagePaths.map(path => deleteProductImage(path))
  await Promise.allSettled(deletePromises)
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

// Validate product image file (larger size limit)
export const validateProductImageFile = (file: File): string | null => {
  if (!file.type.startsWith('image/')) {
    return 'File harus berupa gambar (PNG, JPG, JPEG)'
  }

  if (file.size > 5 * 1024 * 1024) {
    return 'Ukuran file terlalu besar. Maksimal 5MB.'
  }

  return null
}

export { storage }
