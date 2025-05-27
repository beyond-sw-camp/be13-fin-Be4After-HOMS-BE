package com.beyond.homs.common.s3.service;

import com.beyond.homs.common.exception.exceptions.CustomException;
import com.beyond.homs.common.exception.messages.ExceptionMessage;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {
    private static final String S3_BUCKET = System.getenv("AWS_S3_BUCKET");
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/webp");
    private final List<String> ALLOWED_DOC_TYPES = Arrays.asList("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

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

    // 삭제 메서드
    @Override
    public void deleteFile(String key) {
        s3Operations.deleteObject(S3_BUCKET, key);
    }

    // 이미지 출력 메서드
    @Override
    public ResponseEntity<byte[]> getImageView(Resource resource) throws IOException {
        if (resource instanceof S3Resource && resource.exists()) {
            S3Resource s3Resource = (S3Resource) resource;
            String contentType = s3Resource.contentType();

            try (InputStream inputStream = s3Resource.getInputStream()) {
                // 이미지를 바이트 배열로 읽어오기
                byte[] imageBytes = inputStream.readAllBytes();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                // Cache-Control 헤더를 추가하여 브라우저 캐싱을 관리.
                headers.setCacheControl("public, max-age=31536000"); // 1년 캐싱

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (IOException e){
                // 이미지 읽기 실패 처리
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 상품관리 다중 업로드
    @Override
    public Map<String, String> uploadProductFiles(
            Long productId,
            MultipartFile s3Image,
            MultipartFile s3Msds,
            MultipartFile s3Tds1,
            MultipartFile s3Tds2,
            MultipartFile s3Property,
            MultipartFile s3Guide) {

        // 1. 각 MultipartFile이 null이 아닌지, 비어있지 않은지 확인하고 Map에 모으기
        // Map은 파일의 "종류" (키)와 실제 파일 데이터 (MultipartFile)를 매핑합니다.
        Map<String, MultipartFile> incomingFiles = new HashMap<>();
        if (s3Image != null && !s3Image.isEmpty()) incomingFiles.put("s3Image", s3Image);
        if (s3Msds != null && !s3Msds.isEmpty()) incomingFiles.put("s3Msds", s3Msds);
        if (s3Tds1 != null && !s3Tds1.isEmpty()) incomingFiles.put("s3Tds1", s3Tds1);
        if (s3Tds2 != null && !s3Tds2.isEmpty()) incomingFiles.put("s3Tds2", s3Tds2);
        if (s3Property != null && !s3Property.isEmpty()) incomingFiles.put("s3Property", s3Property);
        if (s3Guide != null && !s3Guide.isEmpty()) incomingFiles.put("s3Guide", s3Guide);

        // 2. 파일 타입/확장자 유효성 검증
        for (Map.Entry<String, MultipartFile> entry : incomingFiles.entrySet()) {
            String fieldName = entry.getKey();
            MultipartFile file = entry.getValue();
            String mimeType = file.getContentType();

            boolean isAllowed = false;
            if ("s3Image".equals(fieldName)) { // s3Image 필드는 이미지 타입만 허용
                isAllowed = ALLOWED_IMAGE_TYPES.contains(mimeType);
            } else { // 그 외 필드들 (문서)은 문서 타입만 허용
                isAllowed = ALLOWED_DOC_TYPES.contains(mimeType);
            }

            if (!isAllowed) {
                // 허용되지 않는 파일 형식일 경우 예외 발생
                throw new CustomException(ExceptionMessage.UNSUPPORTED_FILE_TYPE
                );
            }
        }

        // 4. S3 업로드 로직 및 업로드된 S3 Key 수집
        Map<String, String> uploadedFileKeys = new HashMap<>(); // S3 Key (폴더 경로 포함 파일명)를 저장할 맵
        try {
            for (Map.Entry<String, MultipartFile> entry : incomingFiles.entrySet()) {
                String fieldName = entry.getKey();
                MultipartFile file = entry.getValue();

                String s3FolderPath = "product/"+productId+"/"; // 각 파일 종류에 맞는 S3 폴더 경로 결정
                // switch (fieldName) {
                //     case "s3Image": s3FolderPath = "product-images/"; break;
                //     case "s3Msds": s3FolderPath = "product-documents/msds/"; break;
                //     case "s3Tds1": s3FolderPath = "product-documents/tds1/"; break;
                //     case "s3Tds2": s3FolderPath = "product-documents/tds2/"; break;
                //     case "s3Property": s3FolderPath = "product-documents/property/"; break;
                //     case "s3Guide": s3FolderPath = "product-documents/guide/"; break;
                //     default: s3FolderPath = "other-product-files/"; break; // 예상치 못한 필드명
                // }

                // S3에 저장될 고유한 키(경로 + 파일명)를 생성
                String s3Key = generateS3Key(s3FolderPath, Objects.requireNonNullElse(file.getOriginalFilename(), "unknown"));

                // 기존 uploadFile 메서드 호출
                uploadFile(file, s3Key); // S3ServiceImpl의 다른 메서드 호출
                uploadedFileKeys.put(fieldName, s3Key); // 업로드된 S3 Key를 맵에 저장
            }
        } catch (IOException e) {
            // S3 업로드 중 IO 오류 발생 시
            throw new CustomException(ExceptionMessage.FILE_UPLOAD_FAILED);
        } catch (Exception e) {
            // 그 외 예외 발생 시 (예: S3 연결 오류 등)
            throw new CustomException(ExceptionMessage.INTERNAL_SERVER_ERROR);
        }

        // 5. 모든 파일 업로드가 성공적으로 완료된 후 S3 키 맵 반환
        return uploadedFileKeys;
    }

    /**
     * S3에 저장될 고유한 Key를 생성합니다.
     * 폴더 경로와 원본 파일명에 UUID를 더하여 중복을 방지합니다.
     *
     * @param folderPath S3 버킷 내의 폴더 경로 (예: "product-images/")
     * @param originalFilename 원본 파일명 (확장자를 얻기 위함)
     * @return S3에 저장될 고유한 Key (경로 + UUID 파일명)
     */
    @Override
    public String generateS3Key(String folderPath, String originalFilename) {
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        return folderPath + uniqueFileName;
    }
}
