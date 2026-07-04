package com.TaskManagementToolB_ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagementToolB.Cloud.StorageService;

//import com.TaskManagementToolB_Service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {

        try {

            // Create upload folder if not exists
            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique file name
            String originalFileName = file.getOriginalFilename();

            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

            // File path
            Path filePath = Paths.get(uploadDir, uniqueFileName);

            // Copy file
            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return uniqueFileName;

        } catch (IOException e) {

            throw new RuntimeException("File upload failed : " + e.getMessage());
        }
    }

	@Override
	public String store(MultipartFile file, String folder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] read(String storagePath) {
		// TODO Auto-generated method stub
		return null;
	}
}