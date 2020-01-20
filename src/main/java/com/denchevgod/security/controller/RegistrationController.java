package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.persistence.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.save(user);
        return new ModelAndView("redirect:/login");
    }
}
