import { usePermissions } from "../hooks/usePermissions";

export default function CanAny({
  permissions,
  children,
  fallback = null
}) {
  const { hasAnyPermission } = usePermissions();

  if (!hasAnyPermission(permissions)) {
    return fallback;
  }

  return children;
}