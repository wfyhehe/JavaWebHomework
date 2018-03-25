package com.wfy.web.controller;

import com.wfy.web.common.Const;
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
        return new ResponseEntity<>(documents, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/api/document/{documentId}")
    public ResponseEntity<Document> retrieve(@PathVariable Long documentId) {
        Document document = documentService.retrieve(documentId);
        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(document, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/api/document")
    public ResponseEntity<String> retrieve(
            @RequestBody Document document,
            HttpServletRequest request
    ) {
        Long uid = (Long) request.getAttribute("uid");
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < Const.CR) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        documentService.create(document, uid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/document/{documentId}")
    public ResponseEntity<String> update(
            @RequestBody Document document,
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Long uid = (Long) request.getAttribute("uid");
        Document oldDocument = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");
        boolean isContributor = false;
        if (authority == Const.CR) {
            for (User contributor : oldDocument.getContributors()) {
                if (contributor.getId().equals(uid)) {
                    isContributor = true;
                    break;
                }
            }
        }

        if (authority < Const.CRUD && !isContributor) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setId(documentId);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/api/document/{documentId}/approve")
    public ResponseEntity<String> approve(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Document document = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");

        if (authority < Const.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setStatus(Const.APPROVED);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/api/document/{documentId}/deny")
    public ResponseEntity<String> deny(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Document document = documentService.retrieve(documentId);
        Integer authority = (Integer) request.getAttribute("authority");

        if (authority < Const.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        document.setStatus(Const.DENIED);
        documentService.update(document);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/api/document/{documentId}")
    public ResponseEntity<String> delete(
            @PathVariable Long documentId,
            HttpServletRequest request
    ) {
        Integer authority = (Integer) request.getAttribute("authority");
        if (authority < Const.CRUD) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        documentService.delete(documentId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}