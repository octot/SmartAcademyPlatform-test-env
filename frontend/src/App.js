import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import LoginPage from './modules/auth/pages/LoginPage';
import DashboardPage from "./modules/dashboard/pages/DashboardPage";
import ProtectedRoute from "./modules/routes/ProtectedRoute";
import TutorsPage from "./modules/dashboard/pages/TutorsPage";
import AdminPage from "./modules/dashboard/pages/AdminPage"
import StudentsPage from "./modules/dashboard/pages/StudentsPage"
import DashboardLayout from "./modules/layouts/DashboardLayout"
import PERMISSIONS from "./modules/shared/constants/permissions"
import UnauthorizedPage from "./modules/shared/pages/UnauthorizedPage";
import RegisterPage from "./modules/auth/pages/RegisterPage";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Not recommened */}
        <Route path="/" element={<LoginPage />} />

        {/* AUTH ROUTES */}
        <Route path="/login" element={<LoginPage />} />


        {/* Register Page */}

        <Route path="/register" element={<RegisterPage />} />

        {/* DASHBOARD */}
        <Route path="/dashboard" element={<ProtectedRoute>
          <DashboardLayout />
        </ProtectedRoute>}>
          <Route index element={<DashboardPage />} />
          <Route path="students" element={
            <ProtectedRoute permission={PERMISSIONS.STUDENT.VIEW} >
              <StudentsPage />
            </ProtectedRoute>
          } />
          <Route path="tutors"
            element={
              <ProtectedRoute permis sion={PERMISSIONS.TUTOR.VIEW}>
                <TutorsPage />
              </ProtectedRoute>
            } />
          <Route path="admin" element={
            <ProtectedRoute permission={PERMISSIONS.ADMIN.ACCESS}>
              <AdminPage />
            </ProtectedRoute>
          } />
        </Route>

        <Route path="/unauthorized" element={<UnauthorizedPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;




