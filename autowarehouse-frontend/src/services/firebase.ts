import { initializeApp } from 'firebase/app'
import { getStorage, ref, uploadBytes, getDownloadURL, deleteObject } from 'firebase/storage'

// Firebase configuration
// TODO: Replace with your actual Firebase config
const firebaseConfig = {
  apiKey: "AIzaSyAJRr2uqhmNIY-YfmjOTN3h7ziWtTvL7bA",
  authDomain: "autowarehouse-bac37.firebaseapp.com",
  projectId: "autowarehouse-bac37",
  storageBucket: "autowarehouse-bac37.firebasestorage.app",
  messagingSenderId: "716091869764",
  appId: "1:716091869764:web:2fa14ebf2ec5ac25dd0a11",
  measurementId: "G-BSJ3M97LRE"
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

// Upload review media (images and videos) to Firebase Storage
export const uploadReviewMedia = async (
  file: File,
  onProgress?: (progress: UploadProgress) => void
): Promise<UploadResult> => {
  try {
    // Validate file
    const isImage = file.type.startsWith('image/')
    const isVideo = file.type.startsWith('video/')
    
    if (!isImage && !isVideo) {
      throw new Error('File harus berupa gambar atau video')
    }

    if (file.size > 10 * 1024 * 1024) { // 10MB limit for review media
      throw new Error('Ukuran file terlalu besar. Maksimal 10MB.')
    }

    // Generate unique filename for reviews folder
    const fileName = generateFileName(file.name, 'reviews')
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
    console.error('Error uploading review media:', error)
    throw error
  }
}

// Delete review media from Firebase Storage
export const deleteReviewMedia = async (mediaPath: string): Promise<void> => {
  try {
    if (!mediaPath) return
    
    // Extract path from URL if it's a full URL
    let path = mediaPath
    if (mediaPath.includes('firebase')) {
      // Extract path from Firebase URL
      const url = new URL(mediaPath)
      const pathMatch = url.pathname.match(/\/o\/(.+)\?/)
      if (pathMatch) {
        path = decodeURIComponent(pathMatch[1])
      }
    }

    const storageRef = ref(storage, path)
    await deleteObject(storageRef)
  } catch (error) {
    console.error('Error deleting review media:', error)
    // Don't throw error for delete operations to avoid blocking other operations
  }
}

export { storage }
