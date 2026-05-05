import "./Sidebar.css"
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../../../core/auth/AuthContext";
import menuItems from '../config/menuConfig'
export default function Sidebar() {

    const navigate = useNavigate();
    const location = useLocation();
    const { hasPermission, loading } = useAuth();
    if (loading) return <div>Loading menu...</div>;
    console.log("loading:", loading);
    console.log("permissions:", hasPermission);
    return (
        <div className="sidebar">
            <h2>MyApp</h2>
            <ul>
                {menuItems.map((item) => {
                    if (item.permissions && !hasPermission(item.permissions)) {
                        return null;
                    }
                    const isActive = location.pathname === item.path;
                    return (
                        <li
                            key={item.path}
                            className={isActive ? "active" : ""}
                            onClick={() => navigate(item.path)}
                        >
                            {item.label}
                        </li>)
                })}

            </ul>
        </div>
    )

}