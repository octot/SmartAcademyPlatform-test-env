import AuthLayout from "../../layouts/AuthLayout";
import LoginForm from "../components/LoginForm";

export default function LoginPage() {
    return (
        // Component Composition
        <AuthLayout>
            <LoginForm />
        </AuthLayout>

    )
}