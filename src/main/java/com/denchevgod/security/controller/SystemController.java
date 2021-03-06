package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SystemController {

    private final UserService userService;

    public SystemController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }


    //-------------- Login --------------------------------------------------
    @PreAuthorize("isAnonymous()")
    @RequestMapping(path = "login",method = RequestMethod.GET)
    public String login() {
        return "system/login";
    }
    //-------------- Login --------------------------------------------------


    //-------------- Register -----------------------------------------------
    @PreAuthorize("isAnonymous()")
    @RequestMapping(path = "register",method = RequestMethod.GET)
    public String register(@ModelAttribute("user") User user) {
        return "system/register";
    }


    @PreAuthorize("isAnonymous()")
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String handleRegister(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationError", bindingResult.getAllErrors());
            return "system/register";
        }
        userService.saveNewUser(user);
        redirect.addFlashAttribute("successfulRegistration", "Successfully Registered a new User - " + user.getUsername());
        return "redirect:/login";
    }
    //-------------- Register -----------------------------------------------


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
