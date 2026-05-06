import { createContext, useContext, useState, useEffect } from "react";
import { login as loginApi } from "../../modules/auth/api/authApi";
import { logout } from "../../modules/auth/api/authApi";
import { getMe } from "../api/userApi";
import { toast } from "react-toastify";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [auth, setAuth] = useState({
        user: null,
        roles: [],
        permissions: [],
        loading: true,
        activeRole: null,
        profileCompleted: null
    });
    // 🔥 Fetch /me (SOURCE OF TRUTH)
    const fetchMe = async () => {
        try {
            const data = await getMe();
            setAuth({
                user: data.username,
                roles: data.roles,
                permissions: data.permissions,
                activeRole: data.activeRole,
                loading: false,
                profileCompleted: data.profileCompleted
            });
            console.log("activeRoleFromAuthcontext", data.activeRole);

        } catch (err) {
            if (err?.status === 401) {
                console.log("User not logged in");
                // 🔥 Not logged in → NORMAL
                setAuth({
                    user: null,
                    roles: [],
                    permissions: [],
                    activeRole: null,
                    loading: false
                });
            } else {
                console.error("Unexpected error:", err);
            }
        }
    };

    // 🔥 App Load
    useEffect(() => {
        fetchMe();
    }, []);

    const loginAuth = async (credentials) => {

        try {
            const res = await loginApi(credentials);
            await fetchMe();
            console.log("resFromAUth", res)
            return res;
        } catch (err) {
            const message = err.response?.data?.message || "Login failed";
            toast.error(message);
        }

    }

    const loggingout = async () => {
        try {
            await logout();
            
            setAuth({
                user: null,
                roles: [],
                permissions: [],
                activeRole: null,
                loading: false
            });

        }
        catch (e) {

        }
    }

    const hasPermission = (perm) => {

        // Only restrict things when a permission is explicitly provided.
        if (!perm) return true;

        if (Array.isArray(perm)) {
            return perm.some((p) => auth.permissions?.includes(p))
        }
        console.log("perm", perm)
        return auth.permissions?.includes(perm);
    }
    return (
        //wrapper component
        <AuthContext.Provider value={{
            ...auth, loginAuth, loggingout, hasPermission,
            refreshUser: fetchMe
        }}>
            {children}
        </AuthContext.Provider>
    )
}
export const useAuth = () => useContext(AuthContext);
