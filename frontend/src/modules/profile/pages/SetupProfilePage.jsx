import { useAuth } from "../../../core/auth/AuthContext";
const SetupProfilePage = () => {
  const { activeRole } = useAuth();

  return (
    <div>
      <h2>Complete your {activeRole} profile</h2>
    </div>
  );
};


export default SetupProfilePage;