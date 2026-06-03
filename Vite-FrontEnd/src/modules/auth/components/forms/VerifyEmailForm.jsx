import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { verifyOtp, resentOtp } from "../../api/authApi"
export default function VerifyEmailForm() {

    const [otp, setOtp] = useState("");
    const [error, setError] = useState("");


    const navigate = useNavigate();
    const location = useLocation();

    const login = location.state?.login;

    const purpose = location.state?.otpPurpose;
    
    console.log("location", location);

    // 🚨 Redirect if accessed directly
    useEffect(() => {
        if (!login) {
            
            navigate("/login");
        }
    }, [login, navigate]);


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await verifyOtp({ login, otp , purpose});
            alert("Email verified successfully")
            navigate("/login")
        }
        catch (err) {
            console.error(err)
        }
    }
    // 🔁 Manual resend button
    const handleResend = async () => {
        try {
            await resentOtp({ login });
            alert("OTP resent successfully");
        } catch (err) {
            setError(err?.message || "Failed to resend OTP");
        }
    };

    return (
        <div className="otp-card">
            <h2>Verify Email</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Enter OTP" value={otp} onChange={(e) => setOtp(e.target.value)} className="input-field" />
                <button type="submit">Verify</button>
            </form>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <p style={{ marginTop: "10px" }}>
                Didn’t receive OTP?{" "}
                <span
                    onClick={handleResend}
                    style={{ color: "blue", cursor: "pointer" }}
                >
                    Resend OTP
                </span>
            </p>

        </div>
    )
}