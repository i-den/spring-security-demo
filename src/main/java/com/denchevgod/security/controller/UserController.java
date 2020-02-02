package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import com.denchevgod.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
        return new ModelAndView("users/list", "users", userRepository.findAll());
    }

    // TODO: Add Exception Handling
    @RequestMapping("/users/{id}")
    public ModelAndView showUser(@PathVariable String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return new ModelAndView("/users/show", "user", user.get());
    }
}
