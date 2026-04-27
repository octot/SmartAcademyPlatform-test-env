import { createContext, useContext, useState, useEffect } from "react";
import { login as loginApi } from "../../auth/api/authApi";
import { logout } from "../../auth/api/authApi";
import { toast } from "react-toastify";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [auth, setAuth] = useState({
        user: null,
        roles: [],
        permissions: []
    });
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        const stored = localStorage.getItem("auth");
        if (stored) {
            setAuth(JSON.parse(stored));
        }
        setLoading(false);
    }, []);

    const loginAuth = async (credentials) => {

        try {
            const res = await loginApi(credentials);
            const authData =
            {
                user: res.user,
                roles: res.roles,
                permissions: res.permissions
            }
            setAuth(authData);
            localStorage.setItem("auth", JSON.stringify(authData))
            return authData;
        } catch (err) {
            const message = err.response?.data?.message || "Login failed";
            toast.error(message);
        }

    }

    const loggingout = async () => {
        await logout();
        setAuth(
            {
                user: null,
                roles: [],
                permissions: []
            })
        localStorage.removeItem("auth");
    }

    const hasPermission = (perm) => {

        // Only restrict things when a permission is explicitly provided.
        if (!perm) return true;

        if (Array.isArray(perm)) {
            return perm.some((p) => auth.permissions?.includes(p))
        }
        return auth.permissions?.includes(perm);
    }
    return (
        //wrapper component
        <AuthContext.Provider value={{ auth, loginAuth, loggingout, hasPermission, loading }}>
            {children}
        </AuthContext.Provider>
    )
}
export const useAuth = () => useContext(AuthContext);
