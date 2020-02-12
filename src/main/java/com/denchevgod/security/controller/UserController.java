package com.denchevgod.security.controller;

import com.denchevgod.security.model.User;
import com.denchevgod.security.repository.UserRepository;
import com.denchevgod.security.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public ModelAndView showUser(@PathVariable("id") String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return new ModelAndView("/users/show", "user", user.get());
    }


    // TODO: Add Exception Handling
    @RequestMapping( path = "/users/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditUser(@PathVariable("id") String id) {
        Optional<User> user = userRepository.findById(Long.valueOf(id));
        if (!user.isPresent()) {
            throw new RuntimeException("No User with ID " + id);
        }
        return new ModelAndView("users/edit", "user", user.get());
    }

    @RequestMapping(path = "/users/edit", method = RequestMethod.POST)
    public String handleEditUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("updateError", bindingResult.getAllErrors());
            return "users/edit";
        }
        userService.updateUser(user);
        redirect.addFlashAttribute("updateSuccessful", "Successfully updated User " + user.getUsername());
        return "redirect:/";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, RedirectAttributes redirect) {
        User userToDelete = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (userToDelete != null) {
            userRepository.delete(userToDelete);
            redirect.addFlashAttribute("deletedUser", "Deleted User " + userToDelete.getUsername());
        }
        return "redirect:/";
    }


    @RequestMapping("/profile")
    public String showProfile() {
        return "users/profile";
    }
}
