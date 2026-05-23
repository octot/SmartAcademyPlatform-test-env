import { useMemo } from "react";
import { resolveMenu } from "../../../../src/modules/dashboard/config/menuResolver";
import { filterMenuByPermissions } from "../utils/filterMenuByPermissions";
import { useAuth } from "../../../core/auth/AuthContext"

export function useSidebarMenu() {
    const { activeRole, permissions } = useAuth();

    return useMemo(() => {
        const roleMenu = resolveMenu(activeRole);

        return filterMenuByPermissions(
            roleMenu,
            permissions
        );
    }, [activeRole, permissions]);
}