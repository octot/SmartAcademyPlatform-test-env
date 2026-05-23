import { usePermissions } from "../hooks/usePermissions";

export default function CanAll({
    permissions,
    children,
    fallback = null
}) {
    const { hasAllPermissions } = usePermissions();

    if (!hasAllPermissions(permissions)) {
        return fallback;
    }

    return children;
}