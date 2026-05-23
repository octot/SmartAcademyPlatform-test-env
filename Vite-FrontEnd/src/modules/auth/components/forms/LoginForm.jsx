import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { useAuth } from "@core/auth/AuthContext";

import AuthInput from "@auth/components/ui/AuthInput";
import PasswordInput from "@auth/components/ui/PasswordInput";
import AuthButton from "@auth/components/ui/AuthButton";
import AuthDivider from "@auth/components/ui/AuthDivider";
import { OTP_PURPOSE } from "../../constants/authConstants";
import { handleFormError } from "../../utils/handleFormError";

import "../styles/LoginForm.css";

export default function LoginForm() {

    const { loginAuth } = useAuth();

    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

    const [form, setForm] = useState({
        login: "",
        password: ""
    });

    const [errors, setErrors] = useState({
        login: "",
        password: "",
        general: ""
    });

    const validateField = (name, value) => {

        let error = "";

        switch (name) {

            case "login":

                if (!value.trim()) {
                    error = "Email or username is required";
                }

                break;

            case "password":

                if (!value.trim()) {
                    error = "Password is required";
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
    };

    const validateForm = () => {

        validateField("login", form.login);
        validateField("password", form.password);

        return (
            form.login.trim() &&
            form.password.trim()
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

            const res = await loginAuth(form);

            console.log("Login Success", res);

            navigate("/dashboard");

        } catch (err) {

            const errorCode = err?.code;

            switch (errorCode) {

                case "USER_NOT_FOUND":

                    navigate("/register", {
                        state: {
                            login: form.login
                        }
                    });

                    break;

                case "EMAIL_NOT_VERIFIED":

                    navigate("/verify-email", {
                        state: {
                            login: form.login,
                            triggerOtp: true,
                            otpPurpose: OTP_PURPOSE.PASSWORD_RESET
                        }
                    });

                    break;

                case "INVALID_PASSWORD":

                    setErrors(prev => ({
                        ...prev,
                        password: "Wrong password"
                    }));

                    break;

                default:

                    setErrors(prev => ({
                        ...prev,
                        general:
                            err.message ||
                            "Login failed"
                    }));
            }
            handleFormError(
                err,
                setErrors,
                setGlobalError
            );

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="login-form-container">

            <div className="login-header">

                <h2>Welcome Back</h2>

                <p>
                    Sign in to continue to your account
                </p>

            </div>

            <form
                onSubmit={handleSubmit}
                className="login-form"
            >

                <AuthInput
                    label="Email or Username"
                    name="login"
                    value={form.login}
                    onChange={handleChange}
                    placeholder="Enter email or username"
                    error={errors.login}
                />

                <PasswordInput
                    label="Password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Enter password"
                    error={errors.password}
                />

                {
                    errors.general && (
                        <small className="general-error">
                            {errors.general}
                        </small>
                    )
                }

                <div className="forgot-password-wrapper">

                    <button
                        type="button"
                        className="forgot-password-btn"
                        onClick={() =>
                            navigate("/forgot-password")
                        }
                    >
                        Forgot Password?
                    </button>

                </div>

                <AuthButton
                    type="submit"
                    loading={loading}
                >
                    Sign In
                </AuthButton>

            </form>

            <AuthDivider />

            <div className="social-login-placeholder">

                Social login support coming soon

            </div>

            <div className="login-footer">

                Don&apos;t have an account?

                <Link to="/register">
                    Create Account
                </Link>

            </div>

        </div>
    );
}