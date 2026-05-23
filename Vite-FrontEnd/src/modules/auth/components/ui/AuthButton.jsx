    import "./AuthComponents.css";

export default function AuthButton({
    children,
    loading,
    ...props
}) {

    return (
        <button
            className="auth-button"
            disabled={loading}
            {...props}
        >
            {
                loading
                    ? "Please wait..."
                    : children
            }
        </button>
    );
}