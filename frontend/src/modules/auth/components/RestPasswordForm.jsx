import "./RestPasswordForm.css"
import { useState, useEffect } from "react";
import { resetPassword } from "../api/authApi"
import { useNavigate, useLocation } from "react-router-dom";
export default function RestPasswordForm() {

    const navigate = useNavigate();
    const location = useLocation();

    const login = location?.state?.login;
    const resetToken = location?.state?.resetToken;
    console.log("location Value is ", location);

    useEffect(() => {
        if (!login) {
            navigate("/forgot-password")
        }

    }, [login, navigate])

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");


    if (!login) {
        navigate("/forgot-password")
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (newPassword !== confirmPassword) {
            alert("Password mismatch")
            return;
        }
        try {
            await resetPassword({ resetToken, newPassword })
            navigate("/login")
        }
        catch (err) {
            console.error(err);
        }

    }

    return (
        <div className="reset-card">
            <h2>Reset Password</h2>
            <form onSubmit={handleSubmit}>
                <input type="password"
                    placeholder="New Password"
                    className="input-field"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)} />

                <input type="password"
                    placeholder="Confirm Password"
                    className="input-field"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)} />

                <button type="submit" className="reset-btn">
                    Reset Password →
                </button>
            </form>

        </div>
    )


}