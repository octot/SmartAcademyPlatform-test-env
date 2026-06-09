// modules/auth/api/authApi.js
import api from "../../../core/api/client";

//Login
export const login = async (data) => {
    try {
        const response = await api.post("/auth/login", data);
        return response.data;
    }
    catch (error) {
        console.log("errorLogin", error)
        throw error.response?.data || "Login Failed :(";
    }
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

export const resentOtp = async ({ login, purpose }) => {
    try {
        console.log("resentOtploginotp", login, purpose);
        const response = await api.post("/auth/resend-otp", { login, purpose });
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

export const applyForAdmin = async (payload) => {
    try {
        const response = await api.post(
            "/auth/admin-request",
            payload
        );

        return response.data;
    }
    catch (error) {
        throw error.response?.data || "Admin Apply failed";
    }

};


export const getAdminRequests = async (status,
    page = 0,
    size = 5) => {
    try {
        const response = await api.get(
            "/auth/admin-requests",
            {
                params: {
                    status:
                        status === "ALL"
                            ? undefined
                            : status,
                    page,
                    size
                }
            }
        );

        return response.data;
    }
    catch (error) {
        throw error.response?.data || "get Adminrequests failed";
    }


};

export const approveAdminRequest =
    async (requestId) => {

        try {
            const response =
                await api.patch(
                    `/auth/admin-requests/${requestId}/approve`
                );

            return response.data;
        }
        catch (error) {
            throw error.response?.data || "Admin approval failed";

        }
    };
export const rejectAdminRequest =
    async (
        requestId,
        reason,
        comment
    ) => {

        try {
            const response =
                await api.patch(
                    `/auth/admin-requests/${requestId}/reject`,
                    {
                        reason,
                        comment
                    }
                );

            return response.data;
        }
        catch (error) {
            throw error.response?.data || "Admin rejection failed";
        }

    };