package com.example.fieldwire.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


@Service
public class S3Service {

    @Value("${aws.s3.bucket.original}")
    private String uploadBucketName;

    @Value("${aws.s3.bucket.thumb}")
    private String destinationThumbBucketName;
    @Value("${aws.s3.bucket.large}")
    private String destinationLargeBucketName;

    @Value("${aws.s3.region}")
    private String awsRegion;

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        try {
            s3Client.putObject(PutObjectRequest.builder().bucket(uploadBucketName).key(file.getOriginalFilename())
                            .acl(ObjectCannedACL.PUBLIC_READ)  // set ACL to public-read
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return getFileUrl(uploadBucketName, file.getOriginalFilename());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public boolean deleteFile(String fileName) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder().bucket(uploadBucketName).key(fileName).build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFileUrl(String bucketName, String fileName) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).toString();
    }

    public String getFileThumbUrl( MultipartFile file) {
        return getFileUrl( destinationThumbBucketName , file.getOriginalFilename() );
    }

    public String getFileLargeUrl( MultipartFile file) {
        return getFileUrl( destinationLargeBucketName , file.getOriginalFilename() );
    }
}
