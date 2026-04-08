import axios from "axios"
import { handleApiError } from "./errorHandler";
const api = axios.create(
    {
        baseURL: "http://localhost:8080",
        headers: {
            "Content-Type": "application/json"
        }
    })

//Response interceptor
api.interceptors.response.use((response) => response,
    (error) => { 
        return Promise.reject(handleApiError(error))
    })
    
export default api;    
