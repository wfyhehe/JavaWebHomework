package com.wfy.web.service;

import com.wfy.web.dao.UserDao;
import com.wfy.web.exceptions.UsernameExistsException;
import com.wfy.web.exceptions.UsernameNotExistsException;
import com.wfy.web.exceptions.WrongPasswordException;
import com.wfy.web.exceptions.WrongVCodeException;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.model.enums.UserStatus;
import com.wfy.web.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("authService")
@Transactional
public class AuthService {

    @Resource
    private UserDao userDao;

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;

    public Token signIn(String username, String password)
            throws UsernameNotExistsException, WrongPasswordException, WrongVCodeException {
        Token token;
        if (!userDao.exists(username)) {
            throw new UsernameNotExistsException("用户名不存在");
        }
        String md5Password = MD5Util.getMD5(password);
        User user = userDao.getUserByUsernameAndPassword(username, md5Password);
        if (user == null) {
            throw new WrongPasswordException("密码错误");
        } else {
            user.setLastLoginTime(new Date(System.currentTimeMillis()));
            user.setStatus(UserStatus.ONLINE);
            token = tokenService.createToken(user.getId());
            userDao.update(user);
        }
        return token;
    }

    public void signOut(String userId) {
        if (userId == null) {
            return;
        }
        User user = userDao.getUser(userId);
        user.setStatus(UserStatus.OFFLINE);
        tokenService.deleteToken(userId);
    }

    public void signUp(User user) throws Exception {
        if (userService.usernameExists(user.getUsername())) {
            throw new UsernameExistsException("用户名已存在");
        }
        user.setRealPassword(user.getPassword());
        user.setPassword((MD5Util.getMD5(user.getPassword())));
        user.setCreateTime(new Date(System.currentTimeMillis()));
        user.setLastLoginTime(new Date(System.currentTimeMillis()));
        user.setStatus(UserStatus.ONLINE);
        userDao.insert(user);
    }

    public void resetPassword(String passwordOld, String passwordNew, String
            id) throws WrongPasswordException {
        if (!userDao.checkPassword(MD5Util.getMD5(passwordOld), id)) {
            throw new WrongPasswordException("旧密码错误");
        }
        User user = userDao.getUser(id);
        user.setPassword(MD5Util.getMD5(passwordNew));
        userDao.update(user);
    }


}
