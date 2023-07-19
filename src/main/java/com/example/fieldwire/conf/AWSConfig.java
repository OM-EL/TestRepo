package com.example.fieldwire.conf;

import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AWSConfig {

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.accessKey}")
    private String accessKey;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretAccessKey)))
                .build();
    }
}
