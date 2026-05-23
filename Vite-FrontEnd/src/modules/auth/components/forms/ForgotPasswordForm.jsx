import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

import { forgotPassword } from "@auth/api/authApi";

import AuthInput from "@auth/components/ui/AuthInput";
import AuthButton from "@auth/components/ui/AuthButton";


import { OTP_PURPOSE } from "../../constants/authConstants";
import "../styles/ForgotPasswordForm.css";

export default function ForgotPasswordForm() {

    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);

    const [login, setLogin] = useState("");

    const [errors, setErrors] = useState({
        login: "",
        general: ""
    });

    const validateLogin = (value) => {

        let error = "";

        if (!value.trim()) {

            error = "Email is required";
        }

        setErrors(prev => ({
            ...prev,
            login: error
        }));

        return !error;
    };

    const handleChange = (e) => {

        const value = e.target.value;

        setLogin(value);

        validateLogin(value);
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const isValid = validateLogin(login);

        if (!isValid) return;

        try {

            setLoading(true);

            setErrors(prev => ({
                ...prev,
                general: ""
            }));

            await forgotPassword({ login });
            console.log("I am hitting here! ")
            navigate("/verify-otp", {
                state: { login, otpPurpose: OTP_PURPOSE.PASSWORD_RESET }
            });

        } catch (err) {

            setErrors(prev => ({
                ...prev,
                general:
                    err.message ||
                    "Failed to send OTP"
            }));

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="forgot-password-container">

            <div className="forgot-password-header">

                <h2>Forgot Password</h2>

                <p>
                    Enter your email to receive
                    a verification OTP
                </p>

            </div>

            <form
                onSubmit={handleSubmit}
                className="forgot-password-form"
            >

                <AuthInput
                    label="Email"
                    type="email"
                    name="login"
                    value={login}
                    onChange={handleChange}
                    placeholder="Enter your email"
                    error={errors.login}
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
                    Send OTP
                </AuthButton>

            </form>

            <div className="forgot-password-footer">

                Back to

                <Link to="/login">
                    Sign In
                </Link>

            </div>

        </div>
    );
}