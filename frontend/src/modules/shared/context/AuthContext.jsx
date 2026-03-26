import { createContext, useContext, useState, useEffect } from "react";
import { login as loginApi } from "../../auth/api/authApi";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [auth, setAuth] = useState({
        token: null,
        user: null,
        roles: [],
        permissions: []
    });

    useEffect(() => {
        const stored = localStorage.getItem("auth");
        if (stored) {
            setAuth(JSON.parse(stored));
        }
    }, []);

    const login = async (credentials) => {
        const res = await loginApi(credentials);

        const authData =
        {
            token: res.token,
            user: res.user,
            roles: res.roles,
            permissions: res.permissions
        }
        setAuth(authData);
        localStorage.setItem("auth", JSON.stringify(authData))
   
        return authData;
    }

    const logout = () => {
        setAuth(
            {
                token: null,
                user: null,
                roles: [],
                permissions: []
            })
        localStorage.removeItem("auth");
    }

    const hasPermission = (perm) => {
        return auth.permissions.includes(perm);
    }
    return (
        //wrapper component
        <AuthContext.Provider value={{ auth, login, logout, hasPermission }}>
            {children}
        </AuthContext.Provider>
    )
}
export const useAuth = () => useContext(AuthContext);
