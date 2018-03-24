package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.service.TokenService;
import com.wfy.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/8/7.
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @RequestMapping(value = "check_username.do", method = RequestMethod.GET)
    public ServerResponse<String> checkUsername(String username) {
        return userService.usernameExists(username)
                ? ServerResponse.createByErrorMessage("用户名已存在")
                : ServerResponse.createBySuccess();
    }

//    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
//    public ServerResponse<User> getUserInfo(String token) {
//        if (tokenService.checkToken(token)) {
//            return ServerResponse.createBySuccess(userService.getUser(Token.parseUserId(token)));
//        }
//        return ServerResponse.createBySuccessMessage("用户未登录，无法获取当前用户信息");
//    }


    @RequestMapping(value = "update_information.do", method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(Token token, User user) {
        if (tokenService.checkToken(token)) {
            if (user != null) {
                //TODO
                return null;
            }
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }

    @RequestMapping(value = "get_users.do", method = RequestMethod.POST)
    public ServerResponse<List<User>> getUsers(@RequestBody Map<String, Object> map) {
        String empName = (String) map.get("name");
        String username = (String) map.get("username");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        List<User> users = userService.getUsers(username, empName, pageIndex, pageSize);
        if (users != null) {
            return ServerResponse.createBySuccess(users);
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "get_deleted_users.do", method = RequestMethod.GET)
    public ServerResponse<List<User>> getDeletedUsers() {
        List<User> users = userService.getDeletedUsers();
        if (users != null) {
            return ServerResponse.createBySuccess(users);
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "recover_user.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverUser(String id) {
        if (userService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_user.do", method = RequestMethod.GET)
    public ServerResponse<User> getUser(String id) {
        User user = userService.getUser(id);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        } else {
            return ServerResponse.createByErrorMessage("获取用户失败");
        }
    }

    @RequestMapping(value = "get_me.do", method = RequestMethod.GET)
    public ServerResponse<User> getMe(HttpServletRequest request) {
        String tokenStr = request.getHeader(Const.AUTHORIZATION);
        System.out.println("tokenStr at get_me.do: " + tokenStr);
        if (StringUtils.isNotBlank(tokenStr)) {
            Token token = Token.parse(request.getHeader(Const.AUTHORIZATION));
            String id = token != null ? token.getUserId() : null;
            User user = userService.getUser(id);
            if (user != null) {
                return ServerResponse.createBySuccess(user);
            }
        }
        return ServerResponse.createByErrorMessage("获取用户失败");
    }

    @RequestMapping(value = "update_user.do", method = RequestMethod.POST)
    public ServerResponse<String> updateUser(@RequestBody User user) {
        System.out.println(user);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_user.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (userService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "count_user.do", method = RequestMethod.GET)
    public ServerResponse<Long> getCount() {
        long count;
        try {
            count = userService.countUser();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("获取数量失败");
        }
        return ServerResponse.createBySuccess(count);
    }
}
