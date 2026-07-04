package com.TaskManagemenToolB_Services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.TaskManagementToolB_Entity.Attachement;
//import com.TaskmanagementToolB_Repository.AttachementRepository;
import com.TaskManagementToolB_Repositorye.AttachementRepository;
@Service
public class AttachmentService {

    @Autowired
    private AttachementRepository attachmentRepo;

    @Autowired
    private Cloudinary cloudinary;

    public Attachement upload(Long issueId,
                              MultipartFile file,
                              String uploadedBy) {

        validateFile(file);

        try {

            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "auto");

            Map<?, ?> uploadResult =
                    cloudinary.uploader()
                            .upload(file.getBytes(), options);

            Attachement att = new Attachement();

            att.setIssueId(issueId);
            att.setFileName(file.getOriginalFilename());
            att.setFileContentType(file.getContentType());
            att.setSizeBytes(file.getSize());

            att.setCloudinaryId(
                    uploadResult.get("public_id").toString());

            att.setStoragePath(
                    uploadResult.get("secure_url").toString());

            att.setUploadedBy(uploadedBy);

            return attachmentRepo.save(att);

        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
    }

    public List<Attachement> getFileByIssueId(Long issueId) {
        return attachmentRepo.findByIssueId(issueId);
    }

    public Attachement getFileById(Long id) {
        return attachmentRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("File not found"));
    }

    public void delete(Long id) {

        Attachement attach = getFileById(id);

        try {

            Map<String, Object> options =
                    new HashMap<>();

            options.put("resource_type", "auto");

            cloudinary.uploader()
                    .destroy(
                            attach.getCloudinaryId(),
                            options);

            attachmentRepo.delete(attach);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Delete failed", e);
        }
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException(
                    "File cannot be empty");
        }

        long MAX = 5 * 1024 * 1024;

        if (file.getSize() > MAX) {
            throw new RuntimeException(
                    "Maximum file size is 5 MB");
        }

        List<String> allowed = Arrays.asList(
                "image/png",
                "image/jpeg",
                "application/pdf",
                "text/plain"
        );

        if (!allowed.contains(file.getContentType())) {
            throw new RuntimeException(
                    "Invalid file format");
        }
    }
}