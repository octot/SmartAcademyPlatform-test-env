import "./DashboardLayout.css"
import Sidebar from "../dashboard/components/Sidebar"
import Topbar from "../dashboard/components/Topbar"

export default function DashboardLayout({ children }) {
    return (

        <div className="dashboard-container">
            <Sidebar />

            <div className="dashboard-main">
                <Topbar />
                <div className="dashboard-content">
                    {children}
                </div>

            </div>
        </div>)
}
