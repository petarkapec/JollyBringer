import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import NodePolyfills from 'vite-plugin-node-polyfills';

export default defineConfig({
  plugins: [
    react(),
    NodePolyfills({
      // Omogućava polyfill za "crypto" i ostale Node.js module.
      crypto: true,
    }),
  ],
  resolve: {
    alias: {
      // Koristi `crypto-browserify` kao polyfill za "crypto".
      crypto: 'crypto-browserify',
    },
  },
});
