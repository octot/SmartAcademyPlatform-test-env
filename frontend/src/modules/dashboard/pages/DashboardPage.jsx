import { useAuth } from "../../../core/auth/AuthContext";
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
            <h1>Welcome {auth.user}</h1>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cumque, placeat? Porro adipisci molestias aliquam labore dicta maxime et doloribus praesentium!</p>

        </div>
    )

}