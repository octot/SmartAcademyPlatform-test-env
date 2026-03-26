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
                    "CREATE_USER",
                    "VIEW_DASHBOARD",
                    "ASSIGN_TUTOR"
                ]
            });
        }, 800);
    });
};