import { useEffect, useState } from "react";
import { useNavigate, useLocation, Link } from "react-router-dom";

import {
    verifyOtp,
    resentOtp
} from "@auth/api/authApi";

import {
    OTP_PURPOSE
} from "@auth/constants/authConstants";

import AuthInput from "@auth/components/ui/AuthInput";
import AuthButton from "@auth/components/ui/AuthButton";

import "../styles/VerifyOtpForm.css";

export default function VerifyOtpForm() {

    const navigate = useNavigate();

    const location = useLocation();

    const login = location.state?.login;

    const triggerOtp = location.state?.triggerOtp;


    //TODO Temporary fix
    //Have to VAL_001 use query params
    const purpose =
        location.state?.otpPurpose ||
        OTP_PURPOSE.PASSWORD_RESET;

    console.log("locccaiton", location);

    const [otp, setOtp] = useState("");

    const [loading, setLoading] = useState(false);

    const [resendLoading, setResendLoading] =
        useState(false);

    const [errors, setErrors] = useState({
        otp: "",
        general: ""
    });

    useEffect(() => {

        if (!login) {

            navigate("/forgot-password");
        }

    }, [login, navigate]);

    useEffect(() => {

        if (triggerOtp && login) {

            handleResendOtp();
        }

    }, []);

    const validateOtp = (value) => {

        let error = "";

        if (!value.trim()) {

            error = "OTP is required";
        }
        else if (!/^\d{6}$/.test(value)) {

            error = "OTP must be 6 digits";
        }

        setErrors(prev => ({
            ...prev,
            otp: error
        }));

        return !error;
    };

    const handleChange = (e) => {

        const value = e.target.value;

        setOtp(value);

        validateOtp(value);
    };

    const handleResendOtp = async () => {

        try {

            setResendLoading(true);

            await resentOtp({ login, purpose });

        } catch (err) {

            console.error(err);

        } finally {

            setResendLoading(false);
        }
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        const isValid = validateOtp(otp);

        if (!isValid) return;

        try {

            setLoading(true);

            setErrors(prev => ({
                ...prev,
                general: ""
            }));

            const res = await verifyOtp({
                login,
                otp,
                purpose
            });

            const resetToken = res?.resetToken;

            navigate("/reset-password", {
                state: {
                    login,
                    resetToken
                }
            });

        } catch (err) {

            setErrors(prev => ({
                ...prev,
                general:
                    err.message ||
                    "OTP verification failed"
            }));

        } finally {

            setLoading(false);
        }
    };

    return (

        <div className="verify-otp-container">

            <div className="verify-otp-header">

                <h2>Verify OTP</h2>

                <p>
                    Enter the verification code sent
                    to your email
                </p>

            </div>

            <form
                onSubmit={handleSubmit}
                className="verify-otp-form"
            >

                <AuthInput
                    label="OTP"
                    name="otp"
                    value={otp}
                    onChange={handleChange}
                    placeholder="Enter 6-digit OTP"
                    error={errors.otp}
                    hint="Check your email inbox for the OTP"
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
                    Verify OTP
                </AuthButton>

            </form>

            <div className="otp-actions">

                <button
                    type="button"
                    className="resend-otp-btn"
                    onClick={handleResendOtp}
                    disabled={resendLoading}
                >
                    {
                        resendLoading
                            ? "Sending..."
                            : "Resend OTP"
                    }
                </button>

            </div>

            <div className="verify-otp-footer">

                Back to

                <Link to="/login">
                    Sign In
                </Link>

            </div>

        </div>
    );
}