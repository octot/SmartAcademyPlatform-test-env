import "./AuthComponents.css";

export default function AuthInput({
    label,
    type = "text",
    name,
    value,
    onChange,
    placeholder,
    error,
    hint
}) {

    return (
        <div className="auth-input-group">

            {label && (
                <label className="auth-label">
                    {label}
                </label>
            )}

            <input
                type={type}
                name={name}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                className={`auth-input ${error ? "input-error" : ""}`}
            />

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