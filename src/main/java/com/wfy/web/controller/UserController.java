package com.wfy.web.controller;

import com.wfy.web.model.User;
import com.wfy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/api/user")
    public List<User> list() {
        return userService.list();
    }

}