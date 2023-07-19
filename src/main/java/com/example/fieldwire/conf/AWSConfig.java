package com.example.fieldwire.conf;


import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AWSConfig {


    @Bean
    public S3Client s3Client() {
        return S3Client.builder().build();
    }
}
