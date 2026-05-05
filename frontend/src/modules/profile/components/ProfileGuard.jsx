import { Navigate } from "react-router-dom";
import { useAuth } from "../../../core/auth/AuthContext";

const ProfileGuard = ({ children }) => {
  const { activeRole, profileCompleted } = useAuth();

  const isCompleted = profileCompleted?.[activeRole];

  if (!isCompleted) {
    return <Navigate to="/setup-profile" replace />;
  }

  return children;
};

export default ProfileGuard;