package com.TaskManagementToolIB_Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.TaskManagementToolB_Entity.Attachement;
//import com.TaskManagementToolB_Services.AttachmentService;
import com.TaskManagemenToolB_Services.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload/{issueId}/{uploadedBy}")
    public ResponseEntity<Attachement> upload(
            @PathVariable Long issueId,
            @RequestParam("file") MultipartFile file,
            @PathVariable String uploadedBy) {

        Attachement attachment = attachmentService.upload(issueId, file, uploadedBy);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/issue/{issueId}")
    public ResponseEntity<List<Attachement>> getFileByIssueId(@PathVariable Long issueId) {

        return ResponseEntity.ok(attachmentService.getFileByIssueId(issueId));
    }

    @GetMapping("/download/stream/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {

        Attachement attachment = attachmentService.getFileById(id);

        URL url = new URL(attachment.getStoragePath());

        InputStream inputStream = url.openStream();

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(attachment.getFileContentType()))
                .body(resource);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        attachmentService.delete(id);
        return ResponseEntity.ok("File deleted successfully");
    }
}