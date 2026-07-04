package com.TaskManagementToolB.Cloud;

import java.nio.file.*;
import java.util.UUID;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path baseDir;

    public StorageServiceImpl(Environment env) {

        String dir =
                env.getProperty("attachments.storage.local.base-dir",
                        "uploads");

        this.baseDir = Paths.get(dir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(baseDir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String store(MultipartFile file, String folder) {

        String fileName =
                StringUtils.cleanPath(file.getOriginalFilename());

        String ext = "";

        int index = fileName.lastIndexOf(".");

        if (index > 0) {
            ext = fileName.substring(index);
        }

        String key =
                folder + "/" + UUID.randomUUID() + ext;

        Path target = baseDir.resolve(key);

        try {

            Files.createDirectories(target.getParent());

            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING);

            return target.toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to store file",
                    e);
        }
    }

    @Override
    public byte[] read(String storagePath) {

        try {
            return Files.readAllBytes(Paths.get(storagePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}