package com.xcw.web.service;

import com.xcw.web.dao.TokenDao;
import com.xcw.web.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xcw on 18-3-24, good luck.
 */
@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    public boolean checkToken(Token token) {
        Token tokenFromDb = tokenDao.retrieve(token.getId());
        return token.equals(tokenFromDb);
    }

    public void createOrUpdate(Token token) {
        Token tokenFromDb = tokenDao.retrieve(token.getId());
        if (tokenFromDb == null) {
            tokenDao.create(token);
        } else {
            tokenDao.update(token);
        }
    }

    public void delete(String id) {
        tokenDao.delete(id);
    }
}
