import { useState } from "react"
import { useAuth } from "../../../core/auth/AuthContext";
import { useNavigate } from "react-router-dom";
import api from "../../../core/api/client";

import { toast } from "react-toastify";

const StudentProfileForm = ({ initialData = {}, onSuccess }) => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        studentClass: "",
        syllabus: "",
         ...initialData
    });

    const [submitting, setSubmitting] = useState(false);

    const { refreshUser } = useAuth();

    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            setSubmitting(true);

            await api.post("/api/profile/setup/student", formData);
            toast.success("Profile completed successfully");

            await refreshUser();
            navigate("/dashboard");

        } catch (err) {
            const message =
                err.response?.data?.message || "Failed to save profile";

            toast.error(message);
        } finally {
            setSubmitting(false);
        }
    };

    // 🔥 THIS IS THE MISSING RETURN
    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Student Class</label>
                <input
                    type="text"
                    name="studentClass"
                    value={formData.studentClass}
                    onChange={handleChange}
                    required
                />
            </div>

            <div>
                <label>Syllabus</label>
                <textarea
                    name="syllabus"
                    value={formData.syllabus}
                    onChange={handleChange}
                />
            </div>

            <button type="submit" disabled={submitting}>
                {submitting ? "Saving..." : "Save Profile"}
            </button>
        </form>
    );
};

export default StudentProfileForm;