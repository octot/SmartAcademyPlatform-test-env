import { useState } from "react";
import "./LoginForm.css"
import { useAuth } from "../../shared/context/AuthContext";
import { useNavigate } from "react-router-dom";
export default function LoginForm() {
    const { login } = useAuth();
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();
        console.log("CLICK WORKING ✅");
        try {
            const res = await login({ email, password });
            console.log("Login Success", res);
            navigate("/dashboard");
        }
        catch (err) {
            console.error("Login Failed", err);
        }
    };
    //if i only place return (using asi-automatic semicolon insertion so always add return () instead of )
    return (
        <div className="login-card">
            <h2>Welcome Back</h2>
            <form onSubmit={handleSubmit}>
                <input type="email"
                    placeholder="Enter Email"
                    className="input-field" value={email}
                    onChange={(e) => setEmail(e.target.value)}
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
                {/* <label>
                    <input type="checkbox" />
                    Remember me
                </label> */}
                <span className="forgot-link">Forgot Password?</span>
            </div>
        </div>

    )

}

