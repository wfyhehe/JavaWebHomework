package com.wfy.web.service;

import com.wfy.web.mapper.UserMapper;
import com.wfy.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> list() {
        return userMapper.list();
    }

    public User retrieve(int id) {
        return userMapper.retrieve(id);
    }

    public void create(User user) {
        userMapper.create(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(int id) {
        userMapper.delete(id);
    }


}
