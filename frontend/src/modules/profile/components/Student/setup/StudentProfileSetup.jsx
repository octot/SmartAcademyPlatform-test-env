import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../../../../core/auth/AuthContext";
import StudentSetupWizard from "./StudentSetupWizard";
import { createStudentProfile } from "../../../api/ProfileApi";
import api from "../../../../../core/api/client";

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

            await api.post("/api/profile/setup/student", formData); // ✅ POST

            // await createStudentProfile(formData);
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
        <StudentSetupWizard
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleCreate}
            submitting={submitting}
        />
    );
};

export default StudentProfileSetup;