import "./Topbar.css"
import { useAuth } from "../../shared/context/AuthContext"
export default function Topbar() {
    const { auth, loggingout } = useAuth();
    return (
        <div className="topbar">
            <span>Welcome , {auth.user?.name}</span>

            <button onClick={loggingout}>Logout</button>
        </div>
    )

}