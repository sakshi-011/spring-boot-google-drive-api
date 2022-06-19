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

    @GetMapping({"/list","/list/{parentName}"})
    public ResponseEntity<List<File>> list(@PathVariable(required = false) String parentName) throws IOException, GeneralSecurityException {
        List<File> files = service.listFolderContent(parentName);
        return ResponseEntity.ok(files);
    }

    @GetMapping({"/images","/images/{parentName}"})
    public ResponseEntity<List<File>> listImages(@PathVariable(required = false) String parentName) throws IOException, GeneralSecurityException {
        List<File> imgs = service.listImages(parentName);
        return ResponseEntity.ok(imgs);
    }

    @GetMapping({"/view/images","/view/images/{parentName}"})
    public ResponseEntity<String[]> displayImages(@PathVariable(required = false) String parentName) throws IOException, GeneralSecurityException {
        List<File> imgs = service.listImages(parentName);
        //Display first 15 images
        String[] imageIds = new String[15];
        int i=0;
        for(File image : imgs){
            if(i>14) break;
            imageIds[i++]=image.getId();
        }
        return ResponseEntity.ok(imageIds);
    }

}
