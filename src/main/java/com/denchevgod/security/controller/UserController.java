package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.persistence.UserRepository;
import com.denchevgod.security.service.UserService;
import com.denchevgod.security.validation.EmailExistsException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping
    public ModelAndView list() {
        List<User> users = userRepository.findAll();
        return new ModelAndView("users/list", "users", users);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") User user) {
        return new ModelAndView("users/view", "user", user);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute User user) {
        return "users/form";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("users/form", "formErrors", result.getAllErrors());
        }
        try {
            if (user.getId() == null) {
                userService.registerNewUser(user);
                redirect.addFlashAttribute("globalMessage", "Successfully registered a new user");
            } else {
                userService.updateExistingUser(user);
                redirect.addFlashAttribute("globalMessage", "Successfully edited the user");
            }
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") User user) {
        return new ModelAndView("users/form", "user", user);
    }
}
