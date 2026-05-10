
import { Navigate } from "react-router-dom";
import { useAuth } from "../../core/auth/AuthContext";


export default function ProtectedRoute({ children, permission, role }) {
    const { user, loading, activeRole, hasPermission } = useAuth();

    if (loading) {
        return <div>Loading App::::</div>;
    }

    // Role-based protection
    if (role && activeRole !== role) {
        return <Navigate to="/unauthorized" replace />;
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