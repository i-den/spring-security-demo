package com.denchevgod.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Getter
@Setter
public class User {

    private Long id;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;


    private Calendar created = Calendar.getInstance();

}
