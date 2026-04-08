// modules/auth/api/authApi.js
import api from "../api/client";

//Login
export const login = async (data) => {
    try {
        console.log("CurrentLoginData", data);
        const response = await api.post("/auth/login", data);
        return response.data;
    } catch (error) {
        console.error("fromLogin", error)
        throw error;
    }
}

export const register = async (data) => {
    try {
        const response = await api.post("/auth/register", data);
        return response.data;
    } catch (error) {
        throw error.response?.data || "Registration failed";
    }

};

export const forgotPassword = async (data) => {
    try {
        const response = await api.post("/auth/forgot-password", data);
        return response.data;
    } catch (error) {
        throw error.response?.data || "ForgotPassword failed";
    }
}
export const verifyOtp = async ({ login, otp ,purpose}) => {
    try {
        console.log("verifyOtp",login,otp);
        const response = await api.post("/auth/verify-otp", { login, otp ,purpose });

        return response.data;
    } catch (error) {
        throw error.response?.data || "Invalid OTP";
    }
};

export const resentOtp = async ({ login }) => {
    try {
        console.log("reaching resentOp", login)
        const response = await api.post("/auth/resend-otp", { login });
        console.log("reaching resentOp", response)

        return response.data; 
    } catch (error) {
        throw error.response?.data || "Invalid OTP";
    }
};


export const resetPassword = async ({ resetToken, newPassword }) => {
    try {
        const response = await api.post("/auth/reset-password", { resetToken, newPassword });
        return response.data;
    } catch (error) {
        throw error.response?.data || "Reset failed";
    }
};