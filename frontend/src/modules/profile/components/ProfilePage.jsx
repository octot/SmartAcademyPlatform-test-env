import { useAuth } from "../../../core/auth/AuthContext";
import StudentProfileView from "./Student/StudentProfileView";
import TutorProfileView from "./Tutor/TutorProfileView";

const ProfilePage = () => {
    const { activeRole, loading } = useAuth();

    if (loading) return <div>Loading...</div>;

    if (activeRole === "STUDENT") {
        return <StudentProfileView />;
    }

    if (activeRole === "TUTOR") {
        return <TutorProfileView />;
    }

    return <div>No profile available</div>;
};

export default ProfilePage;