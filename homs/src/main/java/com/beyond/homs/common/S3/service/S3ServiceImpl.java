package com.beyond.homs.common.S3.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {
    private static final String S3_BUCKET = System.getenv("AWS_S3_BUCKET");
    private final S3Operations s3Operations;
    
    // 업로드 메서드
    @Override
    public void uploadFile(MultipartFile multipartFile, String key) throws IOException {
        /* 파일로 부터 InputStream을 얻어오고 사용후 자동으로 닫음
        * key = S3 객체의 키 이름
        * is = 업로드할 파일의 InputStream
        */
        try (InputStream is = multipartFile.getInputStream()) {
            s3Operations.upload(S3_BUCKET, key, is,
                    ObjectMetadata.builder().contentType(multipartFile.getContentType()).build());
        }
    }

    // 다운로드 메서드
    @Override
    public Resource downloadFile(String key) {
        return s3Operations.download(S3_BUCKET, key);
    }
}
