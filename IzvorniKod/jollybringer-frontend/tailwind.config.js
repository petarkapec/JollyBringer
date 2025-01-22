/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        customGray: "rgba(23,23,22,0.91)",
        customGrayLighter: "rgb(55,55,55)",
        background: "var(--background)",
        foreground: "var(--foreground)",
      },
      screens: {
        'mobile': { max: '639px' },
      }
    },
  },
  plugins: [],
}