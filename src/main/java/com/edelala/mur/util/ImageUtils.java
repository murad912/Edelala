package com.edelala.mur.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUtils {

    private static final String UPLOAD_DIR = "uploads";

    public static void saveImageToFileSystem(MultipartFile file, String filename) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("ImageUtils: Saved file: " + filePath.toAbsolutePath());
    }

    public static void deleteImageFromFileSystem(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            System.out.println("ImageUtils: Deleted file: " + filePath.toAbsolutePath());
        } else {
            System.out.println("ImageUtils: File not found for deletion: " + filePath.toAbsolutePath());
        }
    }
}
