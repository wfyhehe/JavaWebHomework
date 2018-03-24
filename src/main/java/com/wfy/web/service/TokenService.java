package com.wfy.web.service;

import com.wfy.web.common.Const;
import com.wfy.web.dao.UserDao;
import com.wfy.web.dao.cache.TokenDao;
import com.wfy.web.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("tokenService")
@Transactional
public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TokenDao tokenDao;

    @Resource
    private UserDao userDao;

    public Token createToken(String userId) {
        String uuid = UUID.randomUUID().toString();
        if (userDao.isSuperAdmin(userId)) {
            char[] buffer = uuid.toCharArray();
            buffer[uuid.length() - 1] = '-'; // 超级管理员的token最后一位设为-, 方便前端判断（在后端无特权）
            uuid = String.valueOf(buffer);
        }
        Token token = new Token(userId, uuid);
        tokenDao.setToken(token, Const.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
        return token;
    }


    public boolean checkToken(Token token) {
        if (!tokenDao.checkToken(token)) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        tokenDao.expireToken(token, Const.TOKEN_EXPIRES_MINUTE, TimeUnit
                .MINUTES);
        return true;
    }

    public void deleteToken(String userId) {
        tokenDao.deleteToken(userId);
    }
}
