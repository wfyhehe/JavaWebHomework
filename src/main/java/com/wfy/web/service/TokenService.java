package com.wfy.web.service;

import com.wfy.web.dao.TokenDao;
import com.wfy.web.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@Service
public class TokenService {

    @Autowired
    private TokenDao tokenDao;

    public boolean checkToken(Token token) {
        Token tokenFromDb = tokenDao.retrieve(token.getId());
        return tokenFromDb.equals(token);
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
