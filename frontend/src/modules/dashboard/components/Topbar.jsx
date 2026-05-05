import "./Topbar.css"
import { useAuth } from "../../../core/auth/AuthContext"
export default function Topbar() {
    const { auth, loggingout } = useAuth();
    console.log("authtoolar",auth )
    return (
        <div className="topbar">
            <span>Hi , {auth?.user}</span>
l̥
            <button onClick={loggingout}>Logout</button>
        </div>
    )

}