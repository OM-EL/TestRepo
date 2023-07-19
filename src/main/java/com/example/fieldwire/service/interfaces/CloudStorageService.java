package com.example.fieldwire.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface CloudStorageService {
    String uploadFile(MultipartFile file);
    boolean deleteFile(String fileName);
    String getFileUrl(String bucketName, String fileName);
    String getFileThumbUrl(MultipartFile file);
    String getFileLargeUrl(MultipartFile file);
}
