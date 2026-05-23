import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from "path"

export default defineConfig({
    plugins: [react()],

    resolve: {
        alias: {
            "@": path.resolve(__dirname, "./src"),
            "@auth": path.resolve(__dirname, "./src/modules/auth"),
            "@shared": path.resolve(__dirname, "./src/shared"),
            "@core": path.resolve(__dirname, "./src/core"),
            "@layouts": path.resolve(__dirname, "./src/layouts"),
            "@components": path.resolve(__dirname, "./src/components")
        }
    }
})