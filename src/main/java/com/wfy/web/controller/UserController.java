package com.wfy.web.controller;

import com.wfy.web.model.User;
import com.wfy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/user/")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping(value = "/api/user/{username}/")
    public User retrieve(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/api/user/")
    public void create(@RequestBody User user) {
        System.out.println(user);
        userService.create(user);
    }
}