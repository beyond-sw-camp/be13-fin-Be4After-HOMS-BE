package com.beyond.homs.common.S3.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    void uploadFile(MultipartFile file, String key) throws IOException;

    Resource downloadFile(String key);
}
