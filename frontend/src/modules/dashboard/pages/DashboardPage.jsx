
import { useAuth } from "../../shared/context/AuthContext";
import { useNavigate } from "react-router-dom";
import DashboardLayout from "../../layouts/DashboardLayout";
export default function DashboardPage() {
    const { auth, hasPermission, logout } = useAuth();
    const navigate = useNavigate();
    const handleLogOut = () => {
        logout();
        navigate("/login");
    }
    return (
        <DashboardLayout>
            <h1>Welcome {auth.user?.name}</h1>

            {hasPermission("CREATE_USER") && (
                <button>Create User</button>
            )}
            <button onClick={handleLogOut}>Logout</button>
        </DashboardLayout>)

}