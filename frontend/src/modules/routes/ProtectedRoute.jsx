
import { Navigate } from "react-router-dom";
import { useAuth } from "../../core/auth/AuthContext";


export default function ProtectedRoute({ children, permission }) {
    const { user, loading, hasPermission } = useAuth();

    console.log("loadinFromProtected",loading);
    if (loading) {
        console.log("permission", permission)
        console.log("hasPermission(permission)", hasPermission(permission))
        return <div>Loading App::::</div>;
    }
    if (!user) {
        return <Navigate to="/login" replace />
    }

    //Route based protection 
    if (permission && !hasPermission(permission)) {
        return <Navigate to="/unauthorized" replace />;
    }

    return children;
}