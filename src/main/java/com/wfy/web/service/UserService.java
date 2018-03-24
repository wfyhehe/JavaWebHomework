package com.wfy.web.service;

import com.wfy.web.dao.UserDao;
import com.wfy.web.model.User;
import com.wfy.web.model.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service("userService")
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    public boolean usernameExists(String username) {
        return userDao.exists(username);
    }

    public List<User> getUsers(String username, String empName
            , Integer pageIndex, Integer pageSize) {
        return userDao.search(username, empName, pageIndex, pageSize);
    }

    public User getUser(String id) {
        return userDao.getUser(id);
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public List<User> getDeletedUsers() {
        return userDao.getDeleted();
    }

    public boolean recover(String id) {
        return userDao.recover(id);
    }

    public void updateUser(User user) {
        User oldUser = userDao.getUser(user.getId());
        if (user.getUsername() == null) {
            user.setUsername(oldUser.getUsername());
        }
        if (user.getCreateTime() == null) {
            user.setCreateTime(oldUser.getCreateTime());
        }
        if (user.getStatus() == null) {
            user.setStatus(oldUser.getStatus());
        }
        if (user.getLastLoginTime() == null) {
            user.setLastLoginTime(oldUser.getLastLoginTime());
        }
        if (user.getRemark() == null) {
            user.setRemark(oldUser.getRemark());
        }
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userDao.merge(user);
    }

    public boolean delete(String id) {
        User user = userDao.getUser(id);
        if (user == null) {
            return false;
        }
        user.setStatus(UserStatus.DELETED);
        userDao.update(user);
        return true;
    }

    public long countUser() {
        return userDao.count();
    }

    public boolean isSuperAdmin(String id) {
        return userDao.isSuperAdmin(id);
    }
}
