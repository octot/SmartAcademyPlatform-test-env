// modules/auth/api/authApi.js

export const login = async (data) => {
    console.log("Mock login called with:", data);

    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                token: "mock-jwt-token-123",
                user: {
                    id: 1,
                    name: "Ajay Kumar"
                },
                roles: ["ADMIN"],
                permissions: [
                    "STUDENT_VIEW",
                    "STUDENT_EDIT",
                    "TUTOR_VIEW"
                    , "ADMIN_ACCESS"
                ]
            });
        }, 800);
    });
};