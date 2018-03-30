package com.wfy.web.controller;

import com.wfy.web.common.DocumentStatus;
import com.wfy.web.common.ResponseMessage;
import com.wfy.web.common.UserAuthority;
import com.wfy.web.model.Document;
import com.wfy.web.model.User;
import com.wfy.web.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wfy on 18-3-24, good luck.
 */
@RestController
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @GetMapping(value = "/api/document")
    public ResponseEntity<List<Document>> list() {
        List<Document> documents = documentService.list();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping(value = "/api/document/{documentId}")
    public ResponseEntity<Document> retrieve(@PathVariable Long documentId) {
        Document document = documentService.retrieve(documentId);
        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @PostMapping(value = "/api/document")
    public ResponseEntity<ResponseMessage> create(
            @RequestBody Document document,
            HttpServletRequest request
    ) {
        Long uid = Long.valueOf((String) request.getAttribute("uid"));
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.CR) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        documentService.create(document, uid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/document/{documentId}")
    public ResponseEntity<ResponseMessage> update(
            @RequestBody Document document,
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Long uid = Long.valueOf((String)request.getAttribute("uid"));
        Document oldDocument = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");
        boolean isContributor = false;
        if (authority == UserAuthority.CR) {
            for (User contributor : oldDocument.getContributors()) {
                if (contributor.getId().equals(uid)) {
                    isContributor = true;
                    break;
                }
            }
        }

        if (authority < UserAuthority.CRUD && !isContributor) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setId(documentId);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/document/{documentId}/approve")
    public ResponseEntity<ResponseMessage> approve(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Document document = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");

        if (authority < UserAuthority.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setStatus(DocumentStatus.APPROVED);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/document/{documentId}/deny")
    public ResponseEntity<ResponseMessage> deny(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Document document = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");

        if (authority < UserAuthority.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setStatus(DocumentStatus.DENIED);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/document/{documentId}")
    public ResponseEntity<ResponseMessage> delete(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        documentService.delete(documentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/api/document/{documentId}/restore")
    public ResponseEntity<ResponseMessage> restore(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < UserAuthority.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        documentService.restore(documentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}