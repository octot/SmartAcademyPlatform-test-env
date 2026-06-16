import { Outlet } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@/core/auth/AuthContext";
import AdminSidebar from "../components/AdminSidebar";

import "../styles/AdminLayout.css";

export default function AdminLayout() {
    const navigate = useNavigate();

    const { loggingout } = useAuth();

    const handleLogout = () => {

        loggingout();

        navigate("/login");
    };
    return (

        <div className="admin-layout">
            <AdminSidebar />
            <div className="admin-main">
                <header className="admin-header">
                    <button
                        className="logout-btn"
                        onClick={handleLogout}
                    >
                        Logout
                    </button>

                </header>
                <main className="admin-content">
                    <Outlet />

                </main>
            </div>


        </div>

    );
}