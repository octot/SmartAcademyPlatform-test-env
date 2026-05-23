import "./Sidebar.css"
import {  NavLink } from "react-router-dom";
import { useSidebarMenu } from "../hooks/useSidebarMenu";
export default function Sidebar() {

    const menu = useSidebarMenu();

    return (
        <aside className="sidebar">
            {
                menu.map((item) => {
                    const Icon = item.icon;
                    return (
                        <NavLink key={item.id} to={item.path}>
                            <Icon size={18} />
                            <span>{item.label}</span>
                        </NavLink>
                    )
                })
            }

        </aside>)


}