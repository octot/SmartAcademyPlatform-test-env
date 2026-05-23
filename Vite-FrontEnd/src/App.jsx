import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import LoginPage from './modules/auth/pages/LoginPage';
import DashboardPage from "./modules/dashboard/pages/DashboardPage";
import ProtectedRoute from "./modules/routes/ProtectedRoute";
import AdminPage from "./modules/dashboard/pages/AdminPage"
import UsersPage from "./modules/dashboard/pages/UsersPage"
import DashboardLayout from "./layouts/DashboardLayout"
import UnauthorizedPage from "./modules/shared/pages/UnauthorizedPage";
import RegisterPage from "./modules/auth/pages/RegisterPage";
import ForgotPasswordPage from "./modules/auth/pages/ForgotPasswordPage";
import VerifyOtpPage from "./modules/auth/pages/VerifyOtpPage"
import RestPasswordForm from "./modules/auth/pages/RestPasswordPage";
import VerifyEmailPage from "./modules/auth/pages/VerifyEmailPage";
import { ToastContainer } from "react-toastify";
import ProfileGuard from "./modules/profile/components/ProfileGuard";
import SetupProfilePage from "./modules/profile/pages/SetupProfilePage";
import ProfilePage from "./modules/profile/components/ProfilePage";
import "react-toastify/dist/ReactToastify.css";

function App() {

  return (
    <BrowserRouter>
      <ToastContainer position="top-right" autoClose={3000} />
      <Routes>
        {/* Not recommened */}
        <Route path="/" element={<LoginPage />} />

        {/* AUTH ROUTES */}
        <Route path="/login" element={<LoginPage />} />


        {/* Register Page */}
        <Route path="/register" element={<RegisterPage />} />


        {/* Forgot Password */}
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />


        <Route path="/verify-email" element={<VerifyEmailPage />} />

        {/* VerifyOtpPage */}

        <Route path="/verify-otp" element={<VerifyOtpPage />} />



        {/* ResetPassword page */}
        <Route path="/reset-password" element={<RestPasswordForm />} />


        {/* DASHBOARD */}
        <Route path="/dashboard" element={
          <ProtectedRoute>
            <ProfileGuard>
              <DashboardLayout />
            </ProfileGuard>
          </ProtectedRoute>
        }>
          <Route index element={<DashboardPage />} />
          
          <Route path="profile" element={<ProfilePage />} />

          <Route
            path="users"
            element={
              <ProtectedRoute
                permissions={[
                  "USER_VIEW_OWN",
                  "USER_VIEW_DEPARTMENT",
                  "USER_VIEW_GLOBAL",
                ]}
              >
                <UsersPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="admin"
            element={
              <ProtectedRoute
                permissions={[
                  "USER_EDIT_GLOBAL",
                ]}
              >
                <AdminPage />
              </ProtectedRoute>
            }
          />
        </Route>
        {/* 🔥 ADD THIS */}
        <Route path="/setup-profile" element={<SetupProfilePage />} />
        <Route path="/unauthorized" element={<UnauthorizedPage />} />


      </Routes>
    </BrowserRouter>
  );
}

export default App;




