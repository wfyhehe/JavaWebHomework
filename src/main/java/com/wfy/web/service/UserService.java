package com.wfy.web.service;

import com.wfy.web.dao.UserDao;
import com.wfy.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> list() {
        return userDao.list();
    }

    public User getUserByUsername(String username) {
        return userDao.retrieveByUsername(username);
    }

    public void create(User user) {
        user.setCreateTime(new Date());
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }


}
