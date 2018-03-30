package com.xcw.web.service;

import com.xcw.web.dao.UserDao;
import com.xcw.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by xcw on 18-3-24, good luck.
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

    public boolean isSuperAdmin(String username) {
        User user = getUserByUsername(username);
        return user != null && user.getAuthority() == 0;
    }

    public boolean isSuperAdmin(Long id) {
        User user = userDao.retrieve(id);
        return user != null && user.getAuthority() == 0;
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
