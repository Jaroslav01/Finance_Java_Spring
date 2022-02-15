package com.example.demo.dto;

import com.example.demo.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotEmpty(message = "Please enter your firstname")
    private String firstname;
    @NotEmpty(message = "Please enter your middlename")
    private String middlename;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
}
