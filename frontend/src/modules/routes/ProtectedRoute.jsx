
import { Navigate } from "react-router-dom";
import { useAuth } from "../shared/context/AuthContext";


export default function ProtectedRoute({ children, permission }) {
    const { auth, loading, hasPermission } = useAuth();
    if (loading) {
        return null;
    }

    if (!auth.user) {
        return <Navigate to="/login" replace />
    }

    //Route based protection 
    if (permission && !hasPermission(permission)) {
        return <Navigate to="/unauthorized" replace />;
    }

    return children;
}