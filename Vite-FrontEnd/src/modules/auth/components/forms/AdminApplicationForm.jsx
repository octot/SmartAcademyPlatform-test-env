import { useState } from "react";

import AuthInput from "@auth/components/ui/AuthInput";
import PasswordInput from "@auth/components/ui/PasswordInput";
import AuthButton from "@auth/components/ui/AuthButton";
import { applyForAdmin } from "../../api/authApi";
import {
    ADMIN_PURPOSE_OPTIONS
} from "@auth/constants/adminPurposeOptions";
import { useNavigate } from "react-router-dom";
import "../styles/AdminApplicationForm.css";
import { OTP_PURPOSE } from "../../constants/authConstants";
function AdminApplicationForm() {

    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: "",
        email: "",
        password: "",
        purpose: "",
        description: ""
    });

    const [errors, setErrors] = useState({});

    const [loading, setLoading] = useState(false);

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
            setLoading(true);
            await applyForAdmin(formData)
            navigate(
                "/verify-otp",
                {
                    state: {
                        login: formData.email,
                        otpPurpose:
                            OTP_PURPOSE.ADMIN_EMAIL_VERIFICATION

                    }
                }
            );
            console.log(formData);

        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };
    return (
        <div className="admin-application-card">
            <div className="admin-application-header">
                <h2>Admin Application</h2>

                <p>
                    Request administrative privileges for the Smart Platform.
                    Your application will be reviewed by the security team.
                </p>
            </div>

            <form
                className="admin-application-form"
                onSubmit={handleSubmit}
            >
                <AuthInput
                    label="Username"
                    name="username"
                    placeholder="j.doe"
                    value={formData.username}
                    onChange={handleChange}
                    error={errors.username}
                    helperText="Unique identifier within the admin ecosystem."
                />

                <AuthInput
                    label="Email"
                    name="email"
                    placeholder="jane.doe@example.com"
                    value={formData.email}
                    onChange={handleChange}
                    error={errors.email}
                />
                <PasswordInput
                    label="Secure Password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    error={errors.password}
                    helperText="Security requirement: 12+ characters including symbols."
                />
                <div className="auth-field">
                    <label>Operational Purpose</label>

                    <select
                        name="purpose"
                        value={formData.purpose}
                        onChange={handleChange}
                        className="auth-select"
                    >
                        <option value="">
                            Select primary responsibility...
                        </option>

                        {ADMIN_PURPOSE_OPTIONS.map((option) => (
                            <option
                                key={option.value}
                                value={option.value}
                            >
                                {option.label}
                            </option>
                        ))}
                    </select>

                </div>
                <div className="auth-field">
                    <label>Request Justification</label>

                    <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        className="auth-textarea"
                        rows={5}
                        placeholder="Explain why you require administrative access and which modules you need to manage..."
                    />
                </div>
                <AuthButton
                    type="submit"
                    loading={loading}
                >
                    Submit Admin Request
                </AuthButton>

                <div className="admin-login-link">
                    Already have access? Sign in to Console
                </div>
            </form>
        </div>

    )
}
export default AdminApplicationForm;