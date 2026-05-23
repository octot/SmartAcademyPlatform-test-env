import { useAuth } from "../../../core/auth/AuthContext";

export default function RoleGuard({
    role,
    children,
    fallback = null
}) {
    const { activeRole } = useAuth();

    if (activeRole !== role) {
        return fallback;
    }

    return children;
}