import "./Topbar.css"
import { useAuth } from "../../shared/context/AuthContext"
export default function Topbar() {
    const { auth, logout } = useAuth();
    return (
        <div className="topbar">
            <span>Welcome , {auth.user?.name}</span>

            <button onClick={logout}>Logout</button>
        </div>
    )

}