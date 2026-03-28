import "./DashboardLayout.css"
import Sidebar from "../dashboard/components/Sidebar"
import Topbar from "../dashboard/components/Topbar"
import { Outlet } from "react-router-dom";


export default function DashboardLayout() {
    return (

        <div className="dashboard-container">
            <Sidebar />
            <div className="dashboard-main">
                <Topbar />
                <div className="dashboard-content">
                    <Outlet />
                </div>

            </div>
        </div>)
}
