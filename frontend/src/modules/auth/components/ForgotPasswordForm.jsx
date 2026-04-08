import "./ForgotPasswordForm.css"
import { useState } from "react";
import { forgotPassword } from "../api/authApi";
import { useNavigate } from "react-router-dom";
export default function ForgotPasswordForm() {
    const [login, setLogin] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await forgotPassword({ login });
            alert("OTP sent to Email")
            console.log("before calling verify otp")
            navigate("/verify-otp", { state: { login } });
            console.log("after calling verify otp")
        }
        catch (err) {
            console.error(err);
        }
    }

    return (
        <div className="forgot-card">
            <h2> Forgot Password</h2>
            <form onSubmit={handleSubmit}
            >
                <input type="email"
                    placeholder="Enter your email"
                    className="input-field"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                />
                <button type="submit" className="forgot-btn"
                >
                    Send OTP →
                </button>

            </form>

        </div>
    )
}