package com.wfy.web.filter;

import com.wfy.web.common.Const;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wfy on 18-3-25, good luck.
 */
public class JwtFilter extends OncePerRequestFilter {
    private List<String> excludeUrlPatterns = new ArrayList<>();
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    {
        excludeUrlPatterns.add("/api/user/signin");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludeUrlPatterns.stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        try {
            //解密token，拿到里面的对象claims
            final Claims claims = Jwts.parser().setSigningKey(Const.JWT_SECRET_KEY)
                    .parseClaimsJws(jwtToken).getBody();
            //将对象传递给下一个请求
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

        chain.doFilter(request, response);
    }
}
