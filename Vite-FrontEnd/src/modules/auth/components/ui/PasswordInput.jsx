import { useState } from "react";
import "./AuthComponents.css";

export default function PasswordInput({
    label,
    name,
    value,
    onChange,
    placeholder,
    error,
    hint
}) {

    const [showPassword, setShowPassword] = useState(false);

    return (
        <div className="auth-input-group">

            {label && (
                <label className="auth-label">
                    {label}
                </label>
            )}

            <div className="password-wrapper">

                <input
                    type={showPassword ? "text" : "password"}
                    name={name}
                    value={value}
                    onChange={onChange}
                    placeholder={placeholder}
                    className={`auth-input ${error ? "input-error" : ""}`}
                />

                <button
                    type="button"
                    className="password-toggle"
                    onClick={() => setShowPassword(!showPassword)}
                >
                    {showPassword ? "Hide" : "Show"}
                </button>

            </div>

            {
                hint && !error && (
                    <small className="auth-hint">
                        {hint}
                    </small>
                )
            }

            {
                error && (
                    <small className="auth-error">
                        {error}
                    </small>
                )
            }

        </div>
    );
}