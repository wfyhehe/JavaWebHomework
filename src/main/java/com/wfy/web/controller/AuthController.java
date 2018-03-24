package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.exceptions.UsernameExistsException;
import com.wfy.web.exceptions.UsernameNotExistsException;
import com.wfy.web.exceptions.WrongPasswordException;
import com.wfy.web.exceptions.WrongVCodeException;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Resource
    private AuthService authService;

    @RequestMapping(value = "sign_in.do", method = RequestMethod.POST)
    public ServerResponse<Token> signIn(@RequestBody Map<String, String> userMap,
                                        HttpServletRequest request) {
        Token token;
        String username = userMap.get("username");
        String password = userMap.get("password");
        try {
            token = authService.signIn(username, password);
        } catch (UsernameNotExistsException | WrongPasswordException | WrongVCodeException e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(token);
    }


    @RequestMapping(value = "sign_out.do", method = RequestMethod.GET)
    public ServerResponse<String> signOut(HttpServletRequest request) {
        Token token = Token.parse(request.getHeader(Const.AUTHORIZATION));
        String id = token != null ? token.getUserId() : null;
        authService.signOut(id);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "sign_up.do", method = RequestMethod.POST)
    public ServerResponse<String> signUp(@RequestBody User user,
                                         HttpServletRequest request) {
        try {
            authService.signUp(user);
        } catch (WrongVCodeException | UsernameExistsException e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(@RequestBody Map<String, String> passwordMap,
                                                HttpServletRequest request) {
        String passwordOld = passwordMap.get("passwordOld");
        String passwordNew = passwordMap.get("passwordNew");
        Token token = Token.parse(request.getHeader(Const.AUTHORIZATION));
        String userId = token != null ? token.getUserId() : null;
        try {
            authService.resetPassword(passwordOld, passwordNew, userId);
        } catch (WrongPasswordException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }
}
