import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import { NodePolyfills } from 'vite-plugin-node-polyfills';

export default defineConfig({
  plugins: [
    react(),
    NodePolyfills({
      // Omogućite podršku za `crypto` i druge Node.js module
      crypto: true,
      buffer: true,
      process: true,
    }),
  ],
});
