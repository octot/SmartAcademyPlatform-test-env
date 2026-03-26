import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import LoginPage from './modules/auth/pages/LoginPage';
import DashboardPage from "./modules/dashboard/pages/DashboardPage";
import ProtectedRoute from "./modules/routes/ProtectedRoute";
import TutorsPage from "./modules/dashboard/pages/TutorsPage";
import AdminPage from "./modules/dashboard/pages/AdminPage"
import StudentsPage from "./modules/dashboard/pages/StudentsPage"
function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Not recommened */}
        <Route path="/" element={<LoginPage />} />

        {/* AUTH ROUTES */}
        <Route path="/login" element={<LoginPage />} />

        {/* DASHBOARD */}
        <Route path="/dashboard" element={<ProtectedRoute>
          <DashboardPage />
        </ProtectedRoute>} />
        <Route
          path="/dashboard/students"
          element={
            <ProtectedRoute>
              <StudentsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/dashboard/tutors"
          element={
            <ProtectedRoute>
              <TutorsPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/dashboard/admin"
          element={
            <ProtectedRoute>
              <AdminPage />
            </ProtectedRoute>
          }
        />


      </Routes>
    </BrowserRouter>
  );
}

export default App;




