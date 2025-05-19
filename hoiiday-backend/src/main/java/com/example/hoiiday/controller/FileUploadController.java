package com.example.hoiiday.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:4200")
public class FileUploadController {

    private final Path uploadDir = Paths.get("uploads");

    public FileUploadController() throws IOException {
        Files.createDirectories(uploadDir);
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file)
            throws IOException {
        String filename = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        Path dest = uploadDir.resolve(filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        // return the URL path clients can use
        String urlPath = "/uploads/" + filename;
        return ResponseEntity.ok(urlPath);
    }
}
