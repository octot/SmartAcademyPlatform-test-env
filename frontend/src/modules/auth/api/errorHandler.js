export const ERROR_CODES = {
    USER_NOT_FOUND: "USER_NOT_FOUND",
    INVALID_PASSWORD: "INVALID_PASSWORD",
    EMAIL_NOT_VERIFIED: "EMAIL_NOT_VERIFIED",
    UNAUTHORIZED: "UNAUTHORIZED",
    FORBIDDEN: "FORBIDDEN",
    SERVER_ERROR: "SERVER_ERROR",
};
export const handleApiError = (error) => {
    const status = error.response?.status;
    const code = error.response?.data?.message;
    
    return {
        status,
        code,
        message: mapErrorMessage(code, status),
        raw: error,
    };
};
const mapErrorMessage = (code, status) => {
    switch (code) {
        case ERROR_CODES.USER_NOT_FOUND:
            return "User not found";
        case ERROR_CODES.INVALID_PASSWORD:
            return "Invalid credentials";
        case ERROR_CODES.EMAIL_NOT_VERIFIED:
            return "Please verify your email";
        default:
            if (status === 500) return "Something went wrong";
            return "Unexpected error";
    }
};