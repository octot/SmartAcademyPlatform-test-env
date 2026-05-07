import api from "../../../core/api/client";

export const getStudentProfile = () =>
    api.get("/api/profile/student");

export const getTutorProfile = () =>
    api.get("/api/profile/tutor");

export const updateStudentProfile = (data) =>
    api.put("/api/profile/student", data);

export const updateTutorProfile = (data) =>
    api.put("/api/profile/tutor", data);