import { usePermissions } from "../hooks/usePermissions";

export default function Can({ permission, children, fallback = null }) {

    const { hasPermission } = usePermissions();

    if (!hasPermission(permission)) {
        return fallback;
    }
    
    return children;

}