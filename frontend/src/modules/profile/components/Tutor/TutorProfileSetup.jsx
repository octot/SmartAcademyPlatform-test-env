import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../../../core/api/client";
import { toast } from "react-toastify";
import { useAuth } from "../../../../core/auth/AuthContext";
import TutorProfileForm from "./TutorProfileForm";
import TutorSetupWizard from "./setup/TutorSetupWizard";
const TutorProfileSetup = () => {
    const { refreshUser } = useAuth();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        aadhaarNumber: "",
        hasVehicle: false,
        vehicleType: "",
        qualification: "",
        experienceYears: "",
        entranceCoaching: false,
        subjects: [],
        classesHandled: [],
        syllabusHandled: [],
        preferredLocations: [],
        remarks: "",
        guidelinesAccepted: false,
        payment: {
            paymentMethod: "GPAY",
            gpayNumber: ""
        }
    });

    const [submitting, setSubmitting] = useState(false);

    const handleCreate = async () => {
        try {
            setSubmitting(true);

            await api.post("/api/profile/setup/tutor", formData); // ✅ POST

            toast.success("Profile completed successfully");

            await refreshUser();
            navigate("/dashboard");

        } catch (err) {
            toast.error(err.response?.data?.message || "Error saving profile");
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <TutorSetupWizard 
            formData={formData}
            setFormData={setFormData}
            onSubmit={handleCreate}
            submitting={submitting}
        />
    );
};

export default TutorProfileSetup;