import { Navigate } from "react-router-dom";
import { useAuth } from "../../../core/auth/AuthContext";

const ProfileGuard = ({ children }) => {
  const { activeRole, profileCompleted, hasPermission } = useAuth();
  if (hasPermission(
    "ADMIN_REQUEST_VIEW_GLOBAL"
  )
  ) {
    return children;
  }
  const isCompleted = profileCompleted?.[activeRole];
  console.log("profileCompleted", profileCompleted)

  if (!isCompleted) {
    return <Navigate to="/setup-profile" replace />;
  }

  return children;
};

export default ProfileGuard;