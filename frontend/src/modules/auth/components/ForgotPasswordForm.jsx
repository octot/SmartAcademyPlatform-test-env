import "./ForgotPasswordForm.css"
import { useState } from "react";
import { forgotPassword } from "../api/authApi";
import { useNavigate } from "react-router-dom";
export default function ForgotPasswordForm() {
    const [email, setEmail] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await forgotPassword({ email });
            alert("OTP sent to Email")
            navigate("/verify-otp", { state: { email } });

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
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <button type="submit" className="forgot-btn"
                >
                    Send OTP →
                </button>

            </form>

        </div>
    )
}