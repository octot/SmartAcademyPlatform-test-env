import PERMISSIONS from "../../shared/constants/permissions"
const menuItems = [
    {
        label: "Dashboard",
        path: "/dashboard",
    },
    {
        label: "Students",
        path: "/dashboard/students",
        permission: PERMISSIONS.STUDENT.VIEW,
    },
    {
        label: "Tutors",
        path: "/dashboard/tutors",
        permission: PERMISSIONS.TUTOR.VIEW,
    },
    {
        label: "Admin Panel",
        path: "/dashboard/admin",
        permission: PERMISSIONS.ADMIN.ACCESS,
    },
];

export default menuItems;