import { toast } from "react-toastify";

export const ERROR_CODES = {
    USER_NOT_FOUND: "USER_NOT_FOUND",
    INVALID_PASSWORD: "INVALID_PASSWORD",
    EMAIL_NOT_VERIFIED: "EMAIL_NOT_VERIFIED",
    UNAUTHORIZED: "UNAUTHORIZED",
    FORBIDDEN: "FORBIDDEN",
    SERVER_ERROR: "SERVER_ERROR",
};
export const handleApiError = (error) => {
    console.log("Error is",error)
    if (error.code === "ERR_NETWORK") {
        return {
            status: null,
            code: "CONNECTION_REFUSED",
            message: "Cannot connect to server. Is backend running?",
            raw: error,
        };
    }

    if (!error.response) {
        return {
            status: null,
            code: "NETWORK_ERROR",
            message: "Network error. Please check your connection.",
            raw: error,
        };
    }

    console.log(error)
    const status = error.response?.status;
    const code = error.response?.data?.errorCode;
    const backendMessage=error.response.data?.message;

    return {
        status,
        code,
        message:backendMessage || fallbackMessage(status),
        raw: error,
    };
};
const fallbackMessage = (status) => {
    if (status === 401) return "Invalid credentials";
    if (status === 403) return "Access denied";
    if (status === 400) return "Invalid request";
    if (status === 500) return "Something went wrong";

    return "Unexpected error";
};
