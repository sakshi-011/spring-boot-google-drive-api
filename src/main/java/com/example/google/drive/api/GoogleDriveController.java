package com.example.google.drive.api;

import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class GoogleDriveController {

    @Autowired
    private GoogleDriveService service;

    @GetMapping({"/files"})
    public ResponseEntity<List<File>> listEverything() throws IOException, GeneralSecurityException {
        List<File> files = service.listEverything();
        return ResponseEntity.ok(files);
    }

    @GetMapping({"/list","/list/{parentId}"})
    public ResponseEntity<List<File>> list(@PathVariable(required = false) String parentId) throws IOException, GeneralSecurityException {
        List<File> files = service.listFolderContent(parentId);
        return ResponseEntity.ok(files);
    }


}
