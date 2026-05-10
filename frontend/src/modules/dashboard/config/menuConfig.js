import PERMISSIONS from "../../shared/constants/permissions"
const menuItems = [
    {
        label: "Dashboard",
        path: "/dashboard",
    },
    {
        label: "Profile",
        path: "/dashboard/profile"
    },
    {
        label: "Users",
        path: "/dashboard/users",
        permissions: [
            PERMISSIONS.USER.VIEW.OWN,
            PERMISSIONS.USER.VIEW.DEPARTMENT,
            PERMISSIONS.USER.VIEW.GLOBAL,
        ],
    },
    {
        label: "Admin Panel",
        path: "/dashboard/admin",
        permissions: [
            PERMISSIONS.USER.EDIT.GLOBAL, // or ADMIN-specific later
        ],
    },
];

export default menuItems;