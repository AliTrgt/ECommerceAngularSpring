package com.example.E_commerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir;
    private final long MAX_FILE_SIZE = 3 * 1024 * 1024 ;
    // Constructor with @Value to read upload directory from application.properties
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().toString();
    }


    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty!");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new RuntimeException("File size exceeds the maximum allowed size of 3 MB.");
        }

        // Validate file type (ensure it's PNG)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("image/png")) {
            throw new RuntimeException("Only PNG files are allowed!");
        }

        // Alternatively, you can validate the file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.toLowerCase().endsWith(".png")) {
            throw new RuntimeException("Only files with .png extension are allowed!");
        }

        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
        Path filePath = Paths.get(uploadDir, fileName);

        // Create directories if they don't exist
        Files.createDirectories(filePath.getParent());

        Files.write(filePath, file.getBytes());

        return "/uploads/" + fileName;
    }
}
