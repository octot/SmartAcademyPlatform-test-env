import { useAuth } from "../../../core/auth/AuthContext";


export function usePermissions() {

    const { permissions = [] } = useAuth();

    const hasPermission = (permission) => {
        return permissions.includes(permission);
    };
    const hasAnyPermission = (requiredPermissions = []) => {
        return requiredPermissions.some((permission) =>
            permissions.includes(permission)
        );
    };
    const hasAllPermissions = (requiredPermissions = []) => {
        return requiredPermissions.every((permission) =>
            permissions.includes(permission)
        );
    };

    return {
        permissions,
        hasPermission,
        hasAnyPermission,
        hasAllPermissions
    };

}