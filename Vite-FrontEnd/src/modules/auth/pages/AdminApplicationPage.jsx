import AuthLayout from "../../../../src/layouts/AuthLayout";
import AdminApplicationForm from "@auth/components/forms/AdminApplicationForm";

function AdminApplicationPage() {
    return (
        <AuthLayout
            title="Admin Portal Access"
        >
            <AdminApplicationForm />
        </AuthLayout>
    );
}

export default AdminApplicationPage;