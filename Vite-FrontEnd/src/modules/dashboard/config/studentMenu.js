import {
  LayoutDashboard,
  BookOpen,
  User
} from "lucide-react";

export const studentMenu = [
  {
    id: "dashboard",
    label: "Dashboard",
    icon: LayoutDashboard,
    path: "/student/dashboard"
  },
  {
    id: "courses",
    label: "My Courses",
    icon: BookOpen,
    path: "/student/courses"
    // permissions: ["COURSE_VIEW_OWN"]
  },
  {
    id: "profile",
    label: "Profile",
    icon: User,
    path: "/student/profile"
  }
];