export function filterMenuByPermissions(menu, permissions = []) {
  return menu.filter((item) => {
    if (!item.permissions) {
      return true;
    }

    return item.permissions.every((permission) =>
      permissions.includes(permission)
    );
  });
}