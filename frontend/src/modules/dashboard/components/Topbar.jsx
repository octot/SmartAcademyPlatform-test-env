import "./Topbar.css"
import { useAuth } from "../../../core/auth/AuthContext"
export default function Topbar() {
    const { user,activeRole,roles,auth, loggingout, switchUserRole } = useAuth();
    console.log("authtoolar", auth)
    return (
        <div className="topbar">
            <span>Hi , {user}</span>
            <select
                value={activeRole}
                onChange={(e) => switchUserRole(e.target.value)}
            >
                {roles.map((role) => (
                    <option key={role} value={role}>
                        {role}
                    </option>
                ))}
            </select>
            <button onClick={loggingout}>Logout</button>

        </div>
    )

}