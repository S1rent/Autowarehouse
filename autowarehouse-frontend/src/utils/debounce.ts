/**
 * Debounce utility function
 * Delays the execution of a function until after a specified wait time has elapsed
 * since the last time it was invoked.
 * 
 * @param func - The function to debounce
 * @param wait - The number of milliseconds to delay
 * @returns A debounced version of the function
 */
export function debounce<T extends (...args: any[]) => any>(
  func: T,
  wait: number
): (...args: Parameters<T>) => void {
  let timeout: NodeJS.Timeout | null = null
  
  return (...args: Parameters<T>) => {
    // Clear the previous timeout if it exists
    if (timeout) {
      clearTimeout(timeout)
    }
    
    // Set a new timeout
    timeout = setTimeout(() => {
      func(...args)
    }, wait)
  }
}

/**
 * Debounce utility function with immediate execution option
 * 
 * @param func - The function to debounce
 * @param wait - The number of milliseconds to delay
 * @param immediate - If true, trigger the function on the leading edge instead of trailing
 * @returns A debounced version of the function
 */
export function debounceImmediate<T extends (...args: any[]) => any>(
  func: T,
  wait: number,
  immediate: boolean = false
): (...args: Parameters<T>) => void {
  let timeout: NodeJS.Timeout | null = null
  
  return (...args: Parameters<T>) => {
    const callNow = immediate && !timeout
    
    if (timeout) {
      clearTimeout(timeout)
    }
    
    timeout = setTimeout(() => {
      timeout = null
      if (!immediate) func(...args)
    }, wait)
    
    if (callNow) func(...args)
  }
}

/**
 * Throttle utility function
 * Ensures a function is called at most once in a specified time period
 * 
 * @param func - The function to throttle
 * @param limit - The number of milliseconds to throttle
 * @returns A throttled version of the function
 */
export function throttle<T extends (...args: any[]) => any>(
  func: T,
  limit: number
): (...args: Parameters<T>) => void {
  let inThrottle: boolean = false
  
  return (...args: Parameters<T>) => {
    if (!inThrottle) {
      func(...args)
      inThrottle = true
      setTimeout(() => {
        inThrottle = false
      }, limit)
    }
  }
}

export default debounce
