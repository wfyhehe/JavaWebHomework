package com.wfy.web.service;

import com.wfy.web.common.DocumentStatus;
import com.wfy.web.dao.DocumentDao;
import com.wfy.web.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentDao documentDao;

    public List<Document> list() {
        return documentDao.list();
    }

    public List<Document> listByUser(Long uid) {
        return documentDao.listByUser(uid);
    }

    public Document retrieve(Long id) {
        return documentDao.retrieve(id);
    }

    public void create(Document document, Long userId) {
        document.setCreateTime(new Date());
        document.setStatus(DocumentStatus.PENDING);
        documentDao.create(document);
        documentDao.createLink(document.getId(), userId);
    }

    public void update(Document document) {
        documentDao.update(document);
    }

    public void delete(Long id) {
        documentDao.delete(id);
    }

    public void recover(Long id) {
        documentDao.recover(id);
    }
}
