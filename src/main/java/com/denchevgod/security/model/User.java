package com.denchevgod.security.model;

import lombok.Getter;
import lombok.Setter;

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

    private Calendar created = Calendar.getInstance();

}
