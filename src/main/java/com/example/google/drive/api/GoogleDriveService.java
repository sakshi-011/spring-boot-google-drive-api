package com.example.google.drive.api;

import com.example.google.drive.api.configuration.GoogleDriveConfig;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleDriveService {

    @Autowired
    private GoogleDriveConfig drive;

    private String getIdFromName(String name) throws IOException, GeneralSecurityException{
        FileList result = drive.getInstance().files().list()
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute();
        for(File file : result.getFiles()){
            if(file.getName().equals(name))
                return file.getId();
        }
        return "root";
    }

    public List<File> listEverything() throws IOException, GeneralSecurityException {
        FileList result = drive.getInstance().files().list()
                .setPageSize(1000)
                .setFields("nextPageToken, files(id, name, size, thumbnailLink, shared)")
                .execute();
        return result.getFiles();
    }

    public List<File> listFolderContent(String parentName) throws IOException, GeneralSecurityException {
        String parentId = parentName==null ? "root" : getIdFromName(parentName);
        String query = "'" + parentId + "' in parents";
        FileList result = drive.getInstance().files().list()
                .setQ(query)
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        return result.getFiles();
    }

    public List<File> listImages(String parentName) throws IOException, GeneralSecurityException{
        String parentId = parentName==null ? "root" : getIdFromName(parentName);
        String query = "'" + parentId + "' in parents and mimeType = 'image/jpeg'";
        FileList result = drive.getInstance().files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name, description, webContentLink, webViewLink)")
                .execute();
        return result.getFiles();
    }

}
