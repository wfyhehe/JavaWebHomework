package com.wfy.web.model;

/**
 * Created by wfy on 18-3-24, good luck.
 */
public class DocumentUserLink {
    private Long id;
    private Long userId;
    private Long documentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
