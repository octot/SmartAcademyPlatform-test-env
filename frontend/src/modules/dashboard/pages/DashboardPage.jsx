import { useAuth } from "../../../core/auth/AuthContext";
import roleDashboardMap from "../roleViews/roleDashboardMap";
export default function DashboardPage() {
    const { activeRole, loading } = useAuth();

    if (loading) return <div>Loading...</div>;

    const RoleComponent = roleDashboardMap[activeRole];

    if (!RoleComponent) {
        return <div>No dashboard available for this role</div>;
    }

    return <RoleComponent />;
}