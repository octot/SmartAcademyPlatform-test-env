package com.authentication.Authenitication.dto;

public class RegisterRequestDTO {


    private String username;
    private String password;
    private String email;

    // REQUIRED: no-args constructor (for Jackson)
    public RegisterRequestDTO() {
    }

    // Optional: all-args constructor
    public RegisterRequestDTO(String username, String password, String userEmail) {
        this.username = username;
        this.password = password;
        this.email = userEmail;
    }

    // getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
