import axios from "axios"
import { handleApiError } from "./errorHandler";
import { toast } from "react-toastify";
const api = axios.create(
    {
        baseURL: "http://localhost:8080",
        headers: {
            "Content-Type": "application/json"
        }
    })

//Response interceptor
api.interceptors.response.use
    ((response) => response,
        (error) => {
            const apiError = handleApiError(error);
            toast.error(apiError.message);
            return Promise.reject(apiError);
        })

export default api;    
