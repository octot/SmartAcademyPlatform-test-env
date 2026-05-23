import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { register } from "@auth/api/authApi";

import ROLES from "@shared/constants/roles";

import AuthInput from "@auth/components/ui/AuthInput";
import PasswordInput from "@auth/components/ui/PasswordInput";
import AuthButton from "@auth/components/ui/AuthButton";
import AuthDivider from "@auth/components/ui/AuthDivider";

import "../styles/RegisterForm.css";
import { OTP_PURPOSE } from "@auth/constants/authConstants";

import { handleFormError } from "../../utils/handleFormError";

export default function RegisterForm() {

    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

    const [form, setForm] = useState({
        username: "",
        email: "",
        password: "",
        role: ROLES.STUDENT
    });

    const [errors, setErrors] = useState({
        username: "",
        email: "",
        password: ""
    });

    const validateField = (name, value) => {

        let error = "";

        switch (name) {

            case "username": {

                const usernameRegex =
                    /^[a-z][a-z0-9._]{2,29}$/;

                if (!value.trim()) {
                    error = "Username is required";
                }
                else if (value.includes("@")) {
                    error = "Username cannot be an email";
                }
                else if (!usernameRegex.test(value)) {
                    error =
                        "Use lowercase letters, numbers, . or _";
                }

                break;
            }

            case "email": {

                const emailRegex =
                    /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;

                if (!value.trim()) {
                    error = "Email is required";
                }
                else if (!emailRegex.test(value)) {
                    error = "Enter a valid email";
                }

                break;
            }

            case "password": {

                if (!value.trim()) {
                    error = "Password is required";
                }
                else if (value.length < 8) {
                    error =
                        "Password must be at least 8 characters";
                }

                break;
            }

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
    };

    const validateForm = () => {

        let valid = true;

        Object.entries(form).forEach(([key, value]) => {

            if (key !== "role") {

                validateField(key, value);

                if (!value.trim()) {
                    valid = false;
                }
            }
        });

        const hasErrors =
            Object.values(errors).some(error => error);

        return valid && !hasErrors;
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const isValid = validateForm();

        if (!isValid) return;

        try {

            setLoading(true);

            await register(form);

            navigate("/verify-email", {
                state: {
                    login: form.email,
                    triggerOtp: true,
                    otpPurpose: OTP_PURPOSE.SIGNUP
                }
            });

        } catch (error) {

            handleFormError(
                error,
                setErrors,
                setGlobalError
            );

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="register-form-container">

            <div className="register-header">

                <h2>Create Account</h2>

                <p>
                    Create your account to continue
                </p>

            </div>

            <form
                onSubmit={handleSubmit}
                className="register-form"
            >

                <AuthInput
                    label="Username"
                    name="username"
                    value={form.username}
                    onChange={handleChange}
                    placeholder="Enter username"
                    error={errors.username}
                    hint={
                        <>
                            Username must:
                            <br />
                            • start with lowercase letter
                            <br />
                            • contain lowercase letters,
                            numbers, . or _
                            <br />
                            • be 3–30 characters long
                        </>
                    }
                />

                <AuthInput
                    label="Email"
                    type="email"
                    name="email"
                    value={form.email}
                    onChange={handleChange}
                    placeholder="Enter email"
                    error={errors.email}
                />

                <PasswordInput
                    label="Password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Enter password"
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

                <div className="role-section">

                    <label className="role-label">
                        Select Role
                    </label>

                    <div className="role-container">

                        <label className="role-option">

                            <input
                                type="radio"
                                name="role"
                                value={ROLES.STUDENT}
                                checked={
                                    form.role === ROLES.STUDENT
                                }
                                onChange={handleChange}
                            />

                            Student

                        </label>

                        <label className="role-option">

                            <input
                                type="radio"
                                name="role"
                                value={ROLES.TUTOR}
                                checked={
                                    form.role === ROLES.TUTOR
                                }
                                onChange={handleChange}
                            />

                            Tutor

                        </label>

                    </div>

                </div>

                <AuthButton
                    type="submit"
                    loading={loading}
                >
                    Create Account
                </AuthButton>

            </form>

            <AuthDivider />

            <div className="social-login-placeholder">

                Social login support coming soon

            </div>

            <div className="register-footer">

                Already have an account?

                <Link to="/login">
                    Sign In
                </Link>

            </div>

        </div>
    );
}