import "./VerifyOtpForm.css"
import { useState } from "react";
import { verifyOtp } from "../api/authApi";
import { useNavigate, useLocation } from "react-router-dom";
export default function VerifyOtpForm() {
    const [otp, setOtp] = useState("");
    const navigate = useNavigate();
    const location = useLocation();

    const email = location.state?.email;

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await verifyOtp({ email, otp });
            navigate("/reset-password", { state: { email } });
        }
        catch (err) {
            console.error(err);
        }
    }

    return (
        <div className="otp-card">
            <h2>Verify OTP</h2>
            <form onSubmit={handleSubmit} >
                <input type="text" placeholder="Enter OTP" className="input-field" value={otp}
                    onChange={(e) => setOtp(e.target.value)} />
            </form>
            <button type="submit" className="otp-btn">
                Verify →
            </button>
        </div>
    )

}