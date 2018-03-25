package com.wfy.web.controller;

import com.wfy.web.common.GlobalConst;
import com.wfy.web.common.UserAuthority;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.service.TokenService;
import com.wfy.web.service.UserService;
import com.wfy.web.utils.MD5;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "/api/user")
    public ResponseEntity<List<User>> list(HttpServletRequest request) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.SUPER_ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<User> users = userService.list();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/{username}")
    public ResponseEntity<User> retrieve(
            @PathVariable String username,
            HttpServletRequest request
    ) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.SUPER_ADMIN) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/api/user/signin")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        String username = user.getUsername();
        User userFromDb = userService.getUserByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<>("Username doesn't exist.", HttpStatus.UNAUTHORIZED);
        }
        if (!MD5.getMD5(user.getPassword()).equalsIgnoreCase(userFromDb.getPassword())) {
            return new ResponseEntity<>("Wrong password.", HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = Jwts.builder()
                .setSubject(userFromDb.getId().toString())
                .claim("uid", userFromDb.getId())
                .claim("authority", userFromDb.getAuthority())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, GlobalConst.JWT_SECRET_KEY)
                .compact();
        tokenService.createOrUpdate(new Token(userFromDb.getId().toString(), jwtToken));
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PostMapping(value = "/api/user/signout")
    public ResponseEntity<String> signOut(HttpServletRequest request) {
        String uid = (String) request.getAttribute("uid");
        tokenService.delete(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/user")
    public ResponseEntity<String> signUp(@RequestBody User user, HttpServletRequest request) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.SUPER_ADMIN) {
            return new ResponseEntity<>("Admin only", HttpStatus.FORBIDDEN);
        }

        String username = user.getUsername();
        User userFromDb = userService.getUserByUsername(username);
        if (userFromDb != null) {
            return new ResponseEntity<>("Username already exist.", HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(MD5.getMD5(user.getPassword()));
        userService.create(user);
        userFromDb = userService.getUserByUsername(user.getUsername());
        String jwtToken = Jwts.builder().
                setSubject(userFromDb.getId().toString())
                .claim("uid", userFromDb.getId())
                .claim("authority", userFromDb.getAuthority())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, GlobalConst.JWT_SECRET_KEY)
                .compact();
        tokenService.createOrUpdate(new Token(userFromDb.getId().toString(), jwtToken));

        return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
    }
}