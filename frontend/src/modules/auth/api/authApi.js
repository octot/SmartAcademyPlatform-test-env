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
                    // , "ADMIN_ACCESS"
                ]
            });
        }, 800);
    });
};

export const register = async (data) => {
    console.log("Registered user: ", data)
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({ message: "User registered successfully" });
        }, 800);
    })

}

export const forgotPassword = async (data) => {

    console.log("Sending otp to", data.email);
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({ message: "User registered successfully" });
        }, 800);
    })
}

export const verifyOtp = async ({ email, otp }) => {
  console.log("Verifying OTP:", email, otp);

  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (otp === "1234") {
        resolve({ message: "OTP valid" });
      } else {
        reject("Invalid OTP");
      }
    }, 800);
  });
};
