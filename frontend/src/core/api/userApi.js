import api from "./client";

export const getMe = async () => {
    const res = await api.get("/api/user/me");
    return res.data;
}