import { NavLink } from "react-router-dom";

import "../styles/AdminSidebar.css";

export default function AdminSidebar() {

    return (

        <aside className="admin-sidebar">
            <div className="sidebar-header">

                <h2>
                    Super Admin
                </h2>

            </div>
            <nav className="sidebar-nav">
                <NavLink
                    to="/admin"
                    end
                    className={({ isActive }) =>
                        isActive
                            ? "sidebar-link active"
                            : "sidebar-link"
                    }
                >
                    Dashboard
                </NavLink>

                <NavLink
                    to="/admin/requests"
                    className={({ isActive }) =>
                        isActive
                            ? "sidebar-link active"
                            : "sidebar-link"
                    }
                >
                    Admin Requests
                </NavLink>
            </nav>

        </aside>

    );
}