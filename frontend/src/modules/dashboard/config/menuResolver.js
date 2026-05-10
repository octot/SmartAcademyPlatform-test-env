import { studentMenu } from "./studentMenu";
import { tutorMenu } from "./tutorMenu";
import { adminMenu } from "./adminMenu";

export function resolveMenu(role) {
  switch (role) {
    case "STUDENT":
      return studentMenu;

    case "TUTOR":
      return tutorMenu;

    case "ADMIN":
      return adminMenu;

    default:
      return [];
  }
}