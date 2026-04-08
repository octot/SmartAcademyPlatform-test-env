import { useState } from "react";
import "./LoginForm.css"
import { useAuth } from "../../shared/context/AuthContext";
import { useNavigate } from "react-router-dom";
export default function LoginForm() {
    const { loginAuth } = useAuth();
    const navigate = useNavigate();
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();
        console.log("CLICK WORKING ✅");
        try {
            const res = await loginAuth({ login, password });
            console.log("Login Success", res);
            navigate("/dashboard");
        }
        catch (err) {
            const errorCode = err?.code;
            console.log("Handled Error:", errorCode);
            switch (errorCode) {
                case "USER_NOT_FOUND":
                    navigate("/register", { state: { login } });
                    break;

                case "EMAIL_NOT_VERIFIED":
                    navigate("/verify-email", { state: { login,triggerOtp:true } });
                    break;

                case "INVALID_PASSWORD":
                    setError("Wrong password");
                    break;

                default:
                    setError(err.message);
            }
        }
    };
    return (
        <div className="login-card">
            <h2>Welcome Back</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Enter email or username"
                    className="input-field"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                />
                <input type="password" placeholder="Enter password" className="input-field" value={password} onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit" className="login-btn" >
                    Sign in →
                </button>
            </form>
            <div className="login-options">
                <button onClick={() => navigate("/register")}>
                    Create an account
                </button>
                <button className="forgot-link" onClick={() => navigate("/forgot-password")}
                >
                    Forgot Password?
                </button>
                {/* <label>
                    <input type="checkbox" />
                    Remember me
                </label> */}
            </div>
        </div>

    )

}

