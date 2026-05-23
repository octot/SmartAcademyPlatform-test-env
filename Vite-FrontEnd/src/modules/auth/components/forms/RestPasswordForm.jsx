import { useState, useEffect } from "react";
import { useNavigate, useLocation, Link } from "react-router-dom";

import { resetPassword } from "@auth/api/authApi";

import PasswordInput from "@auth/components/ui/PasswordInput";
import AuthButton from "@auth/components/ui/AuthButton";

import "../styles/RestPasswordForm.css";

export default function RestPasswordForm() {

    const navigate = useNavigate();

    const location = useLocation();

    const login = location?.state?.login;

    const resetToken = location?.state?.resetToken;

    const [loading, setLoading] = useState(false);

    const [form, setForm] = useState({
        password: "",
        confirmPassword: ""
    });

    const [errors, setErrors] = useState({
        password: "",
        confirmPassword: "",
        general: ""
    });

    useEffect(() => {

        if (!login || !resetToken) {

            navigate("/forgot-password");
        }

    }, [login, resetToken, navigate]);

    const validateField = (name, value) => {

        let error = "";

        switch (name) {

            case "password":

                if (!value.trim()) {

                    error = "Password is required";
                }
                else if (value.length < 8) {

                    error =
                        "Password must be at least 8 characters";
                }

                break;

            case "confirmPassword":

                if (!value.trim()) {

                    error = "Please confirm your password";
                }
                else if (value !== form.password) {

                    error = "Passwords do not match";
                }

                break;

            default:
                break;
        }

        setErrors(prev => ({
            ...prev,
            [name]: error
        }));
    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        setForm(prev => ({
            ...prev,
            [name]: value
        }));

        validateField(name, value);

        if (
            name === "password" &&
            form.confirmPassword
        ) {

            validateField(
                "confirmPassword",
                form.confirmPassword
            );
        }
    };

    const validateForm = () => {

        validateField("password", form.password);

        validateField(
            "confirmPassword",
            form.confirmPassword
        );

        return (
            form.password.trim() &&
            form.confirmPassword.trim() &&
            form.password === form.confirmPassword
        );
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const isValid = validateForm();

        if (!isValid) return;

        try {

            setLoading(true);

            setErrors(prev => ({
                ...prev,
                general: ""
            }));

            await resetPassword({
                resetToken,
                newPassword: form.password
            });

            navigate("/login");

        } catch (err) {

            setErrors(prev => ({
                ...prev,
                general:
                    err.message ||
                    "Password reset failed"
            }));

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="reset-password-container">

            <div className="reset-password-header">

                <h2>Reset Password</h2>

                <p>
                    Create a new secure password
                    for your account
                </p>

            </div>

            <form
                onSubmit={handleSubmit}
                className="reset-password-form"
            >

                <PasswordInput
                    label="New Password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Enter new password"
                    error={errors.password}
                    hint={
                        <>
                            Password should contain:
                            <br />
                            • minimum 8 characters
                            <br />
                            • uppercase letter
                            <br />
                            • number or special character
                        </>
                    }
                />

                <PasswordInput
                    label="Confirm Password"
                    name="confirmPassword"
                    value={form.confirmPassword}
                    onChange={handleChange}
                    placeholder="Confirm new password"
                    error={errors.confirmPassword}
                />

                {
                    errors.general && (
                        <small className="general-error">
                            {errors.general}
                        </small>
                    )
                }

                <AuthButton
                    type="submit"
                    loading={loading}
                >
                    Reset Password
                </AuthButton>

            </form>

            <div className="reset-password-footer">

                Back to

                <Link to="/login">
                    Sign In
                </Link>

            </div>

        </div>
    );
}