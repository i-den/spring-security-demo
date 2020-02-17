package com.denchevgod.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username Cannot be null")
    @Size(min = 1, message = "Username is required")
    @Column(name = "username", unique = true)
    private String username;

    private String password;

    @NotNull(message = "Age Cannot be null")
    @Min(value = 0, message = "Must be above 0")
    @Max(value = 100, message = "Must be below 100")
    @Column(name = "age")
    private Integer age;

}
