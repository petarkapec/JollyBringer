import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import { NodePolyfills } from 'vite-plugin-node-polyfills';

export default defineConfig({
  plugins: [
    react(),
    NodePolyfills({
      crypto: true, // Omogućite polyfill za "crypto"
    }),
  ],
  resolve: {
    alias: {
      crypto: 'crypto-browserify', // Polyfill za "crypto"
    },
  },
});
