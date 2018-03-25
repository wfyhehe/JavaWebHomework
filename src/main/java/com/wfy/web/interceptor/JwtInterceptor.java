package com.wfy.web.interceptor;

import com.wfy.web.common.Const;
import com.wfy.web.model.Token;
import com.wfy.web.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(Const.JWT_SECRET_KEY)
                    .parseClaimsJws(jwtToken).getBody();
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        String uid = ((Integer) claims.get("uid")).toString();
        Token token = new Token(uid, jwtToken);
        if (!tokenService.checkToken(token)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        request.setAttribute("uid", uid);
        return true;
    }
}
