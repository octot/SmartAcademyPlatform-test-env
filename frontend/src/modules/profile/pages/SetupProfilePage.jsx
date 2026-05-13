import { useAuth } from "../../../core/auth/AuthContext";
import StudentProfileSetup from "../components/Student/setup/StudentProfileSetup"
import TutorProfileSetup from "../components/Tutor/TutorProfileSetup"
const SetupProfilePage = () => {
    const { activeRole, loading } = useAuth();
    if (loading) {
        return <div>Loading...</div>;
    }
    console.log("loading", loading)
    console.log("activeRole", activeRole)
    return (<div>
        {/* <h2>Complete your profile Bro!</h2> */}
        {activeRole === "STUDENT" && <StudentProfileSetup />}
        {activeRole === "TUTOR" && <TutorProfileSetup />}
    </div>
    );
};


export default SetupProfilePage;