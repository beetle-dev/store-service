package com.cafe.storeservice.aws.service;

import com.cafe.storeservice.common.exception.CustomException;
import com.cafe.storeservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, String directory) {
        String key = directory + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(
                    file.getInputStream(), file.getSize()));

        } catch (IOException e) {
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
        }

        return key;
    }

    public void delete(String key) {
        s3Client.deleteObject(b -> b.bucket(bucket).key(key));
    }

    // todo Presigned URL (프론트엔드 직접 업로드) ────────────
    public String generatePresignedUploadUrl(String key, Duration ttl) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(ttl)
                .putObjectRequest(b -> b.bucket(bucket).key(key))
                .build();
        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    public String generatePresignedDownloadUrl(String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(b -> b.bucket(bucket).key(key))
                .build();
        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
