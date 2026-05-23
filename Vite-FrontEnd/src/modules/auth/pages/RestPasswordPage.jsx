import AuthLayout from "../../../layouts/AuthLayout";
import RestPasswordForm from "../components/forms/RestPasswordForm";

export default function RestPasswordPage() {
    return (
        // Component Composition
        <AuthLayout>
            <RestPasswordForm />
        </AuthLayout>

    )
}