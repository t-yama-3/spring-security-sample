package com.example.demo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupForm {
//    @NotNull
//    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;
    
//    @NotNull
//    @NotEmpty
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
