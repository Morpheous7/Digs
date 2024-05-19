package com.digs.dig0.controller;

import com.digs.dig0.model.User;
import com.digs.dig0.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Copyright to Digs LLC
 * @author Ike Kennedy
 */

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Optional<User> user = userService.findById(id);
        model.addAttribute("user", user.get());
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user){
        userService.updateUser(id, user.getName(), user.getPhn(), user.getUsername());
        return "redirect:/users?update_success";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/users?delete_success";
    }
}