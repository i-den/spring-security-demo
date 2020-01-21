package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.persistence.UserRepository;
import com.denchevgod.security.service.UserService;
import com.denchevgod.security.validation.EmailExistsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "signup")
    public ModelAndView registrationForm() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping(value = "user/register")
    public ModelAndView registerUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("register", "user", user);
        }
        try {
            userService.registerNewUser(user);
        } catch (EmailExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("register", "user", user);
        }
        return new ModelAndView("redirect:/login");
    }
}
