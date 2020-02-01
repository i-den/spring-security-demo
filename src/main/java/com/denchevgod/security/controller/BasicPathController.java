package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class BasicPathController {

    private final UserService userService;

    public BasicPathController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping(path = "login",method = RequestMethod.GET)
    public String login() {
        return "system/login";
    }

    //-------------- Register -----------------------------------------------
    @RequestMapping(path = "register",method = RequestMethod.GET)
    public String register(@ModelAttribute("user") User user) {
        return "system/register";
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String handleRegister(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", bindingResult.getAllErrors());
            return "system/register";
        }
        userService.saveNewUser(user);
        model.addAttribute("successfulRegistration", "Successfully Register a new User");
        return "redirect:/";
    }
    //-------------- Register -----------------------------------------------

}
