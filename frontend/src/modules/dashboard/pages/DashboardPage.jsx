import { useAuth } from "../../shared/context/AuthContext";
import { useNavigate } from "react-router-dom";
export default function DashboardPage() {
    const { auth, hasPermission, loggingout } = useAuth();
    console.log("DasHboard", auth);
    const navigate = useNavigate();
    const handleLogOut = () => {
        loggingout();
        navigate("/login");
    }
    return (
        <div>
            <h1>Welcome {auth.user?.name}</h1>

            {hasPermission("ADMIN_CREATE_GLOBAL") && (
                <button>Create User</button>
            )}

        </div>
    )

}