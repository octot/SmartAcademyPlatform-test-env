import { useAuth } from "../../../core/auth/AuthContext";
import StudentProfileForm from "../components/StudentProfileForm"
import TutorProfileForm from "../components/TutorProfileForm"
const SetupProfilePage = () => {
    const { user, roles, permissions, activeRole, loading } = useAuth();
    if (loading) {
        return <div>Loading...</div>;
    }
    console.log("loading", loading)
    console.log("activeRole", activeRole)
    return (<div>
        <h2>Complete your profile Bro!</h2>
        {activeRole === "STUDENT" && <StudentProfileForm />}
        {activeRole === "TUTOR" && <TutorProfileForm />}
    </div>
    );
};


export default SetupProfilePage;