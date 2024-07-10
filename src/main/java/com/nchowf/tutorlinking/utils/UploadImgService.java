package com.nchowf.tutorlinking.utils;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class UploadImgService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();
    @Value("${gg-driver.root-url}")
    private String ROOT_URL;
    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "gg-drive-api-config.json");
        return filePath.toString();
    }

    private Drive createDriveService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, credential)
                .build();
    }
    public String uploadFileToDrive(File file, String folder_id) throws GeneralSecurityException, IOException {
        Drive drive = createDriveService();
        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        fileMetaData.setName(file.getName());
        fileMetaData.setParents(Collections.singletonList(folder_id));
        FileContent mediaContent = new FileContent("image/jpg", file);
        Drive.Files.Create createRequest = drive.files().create(fileMetaData, mediaContent).setFields("id");
        createRequest.getMediaHttpUploader().setDirectUploadEnabled(false);
        com.google.api.services.drive.model.File uploadFile = createRequest.execute();
        file.delete();
        return ROOT_URL + uploadFile.getId();
    }
    public void deleteFiles(String[] ids) throws GeneralSecurityException, IOException {
        Drive drive = createDriveService();
        for(String id : ids){
            try {
                drive.files().delete(id).execute();
            } catch (IOException e) {
                System.err.println("Không thể xóa tệp tin: " + id);
                e.printStackTrace();
            }
        }
    }
}
