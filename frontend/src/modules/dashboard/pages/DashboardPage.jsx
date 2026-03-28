import { useAuth } from "../../shared/context/AuthContext";
import { useNavigate } from "react-router-dom";
export default function DashboardPage() {
    const { auth, hasPermission, logout } = useAuth();
    const navigate = useNavigate();
    const handleLogOut = () => {
        logout();
        navigate("/login");
    }
    return (
        <div>
            <h1>Welcome {auth.user?.name}</h1>

            {hasPermission("CREATE_USER") && (
                <button>Create User</button>
            )}

            <button onClick={handleLogOut}>Logout</button>

        </div>
    )

}