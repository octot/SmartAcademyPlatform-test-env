import "./VerifyOtpForm.css"
import { useState, useEffect } from "react";
import { verifyOtp ,resentOtp} from "../api/authApi";
import { useNavigate, useLocation } from "react-router-dom";
import { OTP_PURPOSE } from "../constants/authConstants";
export default function VerifyOtpForm() {
    const [otp, setOtp] = useState("");
    const navigate = useNavigate();
    const location = useLocation();

    const login = location.state?.login;
    const triggerOtp = location.state?.triggerOtp;

    useEffect(() => {
        if (!login) {
            navigate("/forgot-password")
        }

    }, [login, navigate])
    useEffect(() => {
        if (triggerOtp) {
            resentOtp({ login });
        }
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res=await verifyOtp({ login, otp,  purpose: OTP_PURPOSE.PASSWORD_RESET});
            console.log("resultfromVerify",res)
            const resetToken=res?.resetToken
            navigate("/reset-password", { state: {login,resetToken} });
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
                <button type="submit" className="otp-btn">
                    Verify →
                </button>
            </form>

        </div>
    )

}