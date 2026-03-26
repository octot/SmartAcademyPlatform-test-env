import "./AuthLayout.css"

export default function AuthLayout({ children }) {
    return (
        <div className="auth-container">
            <div className="auth-left">
                <h1>Welcome Back!</h1>
                <p>Enter details to get your personal account</p>
                <div className="auth-buttons">
                    <button className="btn-outline">Sign In</button>
                    <button className="btn-filled">Sign Up</button>
                </div>
            </div>
            <div className="auth-right">
                {children}
            </div>
        </div>)

}