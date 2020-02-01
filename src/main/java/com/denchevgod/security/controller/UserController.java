package com.denchevgod.security.controller;

import com.denchevgod.security.repository.UserRepository;
import com.denchevgod.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @RequestMapping("/users")
    public ModelAndView listUsers() {
        return new ModelAndView("users/index", "users", userRepository.findAll());
    }
}
