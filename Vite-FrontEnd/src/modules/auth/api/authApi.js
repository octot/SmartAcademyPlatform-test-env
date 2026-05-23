// modules/auth/api/authApi.js
import api from "../../../core/api/client";

//Login
export const login = async (data) => {
    const response = await api.post("/auth/login", data);
    return response.data;
}
export const logout = async () => {
    try {
        await api.post("/auth/logout");
        console.log("Triggered!")
    } catch (err) {

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
export const verifyOtp = async ({ login, otp, purpose }) => {
    try {
      

        const response = await api.post("/auth/verify-otp", { login, otp, purpose });

        return response.data;
    } catch (error) {
        throw error.response?.data || "Invalid OTP In VerifyOtp";
    }   
};

export const resentOtp = async ({ login ,purpose}) => {
    try {
          console.log("resentOtploginotp", login, purpose);
        const response = await api.post("/auth/resend-otp", { login,purpose });
        console.log("reaching resentOp", response)

        return response.data;
    } catch (error) {
        throw error.response?.data || "Invalid OTP in ResentOtp";
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