import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import LoginPage from './modules/auth/pages/LoginPage';
import DashboardPage from "./modules/dashboard/pages/DashboardPage";
import ProtectedRoute from "./modules/routes/ProtectedRoute";
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
      </Routes>
    </BrowserRouter>
  );
}

export default App;




