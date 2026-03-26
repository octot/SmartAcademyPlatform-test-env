const menuItems = [
    {
        label: "Dashboard",
        path: "/dashboard",
    },
    {
        label: "Students",
        path: "/dashboard/students",
        permission: "VIEW_STUDENTS",
    },
    {
        label: "Tutors",
        path: "/dashboard/tutors",
        permission: "VIEW_TUTORS",
    },
    {
        label: "Admin Panel",
        path: "/dashboard/admin",
        permission: "ADMIN_ACCESS",
    },
];

export default menuItems;