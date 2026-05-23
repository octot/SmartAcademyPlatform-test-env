
import {
    LayoutDashboard,
    Users,
    User
} from "lucide-react";

export const tutorMenu = [
    {
        id: "dashboard",
        label: "Dashboard",
        icon: LayoutDashboard,
        path: "/dashboard"
    },
    {
        id: "students",
        label: "Students",
        icon: Users,
        path: "/students",
        permissions: ["STUDENT_VIEW"]
    },
    {
        id: "profile",
        label: "Profile",
        icon: User,
        path: "/dashboard/profile"
    }
];