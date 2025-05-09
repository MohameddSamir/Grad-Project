package com.favtour.travel.core.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

@Service
public class FileStorageService {

    public void saveImage(MultipartFile image, String uploadDir, String fileName) throws IOException {

        Path uploadPath= Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream= image.getInputStream();
        Path path= uploadPath.resolve(fileName);
        Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteImagesWithDirectory(String deleteDir) throws IOException {

        Path path= Paths.get(deleteDir).toAbsolutePath();
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to delete: " + p, e);
                        }
                    });
        }
    }
}
