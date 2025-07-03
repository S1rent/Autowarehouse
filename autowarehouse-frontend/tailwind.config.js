/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#1e40af",
        secondary: "#3b82f6",
        accent: "#f59e0b",
        dark: "#1f2937",
        light: "#f8fafc"
      },
      fontFamily: {
        sans: [
          "Inter",
          "sans-serif"
        ]
      }
    },
  },
  plugins: [],
}
