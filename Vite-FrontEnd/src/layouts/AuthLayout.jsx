import "./AuthLayout.css"

export default function AuthLayout({ children }) {
    return (
        <div className="auth-page">

            <div className="auth-card-wrapper">

                <div className="auth-header">
                    <h1>Smart Platform</h1>
                    <p className="auth-subtitle">
                        Secure authentication platform
                    </p>
                </div>

                <div className="auth-card">
                    {children}
                </div>

            </div>

        </div>
    );

}