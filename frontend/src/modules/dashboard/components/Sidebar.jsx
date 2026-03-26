import "./Sidebar.css"
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../../shared/context/AuthContext";
import menuItems from '../config/menuConfig'
export default function Sidebar() {
    const navigate = useNavigate();
    const location = useLocation();
    const { hasPermission } = useAuth();
    return (
        <div className="sidebar">
            <h2>MyApp</h2>
            <ul>
                {menuItems.map((item) => {
                    if (item.permission && !hasPermission(item.permission)) { return null; }
                    const isActive = location.pathname == item.path;
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