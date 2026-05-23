import AuthLayout from "../../../layouts/AuthLayout";
import LoginForm from "../components/forms/LoginForm";

export default function LoginPage() {
    return (
        // Component Composition
        <AuthLayout>
            <LoginForm />
        </AuthLayout>

    )
}