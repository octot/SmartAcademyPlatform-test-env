import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../../../core/auth/AuthContext";
import StudentProfileForm from "./StudentProfileForm";
import { createStudentProfile } from "../../api/ProfileApi";

const StudentProfileSetup = () => {
    const { refreshUser } = useAuth();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        studentClass: "",
        syllabus: ""
    });

    const [submitting, setSubmitting] = useState(false);

    const handleCreate = async () => {
        try {
            setSubmitting(true);
            await createStudentProfile(formData);
            toast.success("Profile completed successfully");
            await refreshUser();
            navigate("/dashboard");

        } catch (err) {
            console.log(err.response?.data?.message || "Error saving profile");
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <StudentProfileForm
            mode="create"
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleCreate}
            submitting={submitting}
        />
    );
};

export default StudentProfileSetup;