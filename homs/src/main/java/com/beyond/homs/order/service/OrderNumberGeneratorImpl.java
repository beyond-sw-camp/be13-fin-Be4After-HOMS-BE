package com.beyond.homs.order.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class OrderNumberGeneratorImpl implements OrderNumberGenerator {

    // 연월(YYMM) 포맷 (예: 2505 for May 2025)
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyMM");

    // 랜덤 문자/숫자 생성에 사용할 문자셋
    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_PART_LENGTH = 3; // XXX (3자리)
    private static final Random RANDOM = new Random(); // 랜덤 객체 인스턴스

    /**
     * H-[YYMM]-[XXX] 패턴의 발주번호를 생성합니다.
     * 예: H-2505-ABC (2025년 5월에 생성된 발주, 랜덤 ABC)
     *
     * @return 생성된 발주번호 문자열
     */
    @Override
    public String generateOrderNumber() {
        String yearMonthPart = LocalDateTime.now().format(YEAR_MONTH_FORMATTER); // 현재 연월(YYMM) 가져오기
        String randomPart = generateRandomAlphaNumeric(RANDOM_PART_LENGTH); // 랜덤 XXX 생성

        // 최종 발주번호 조합
        return "H-" + yearMonthPart + "-" + randomPart;
    }

    /**
     * 지정된 길이만큼 랜덤한 영문 대문자 및 숫자로 구성된 문자열을 생성합니다.
     * @param length 생성할 문자열의 길이
     * @return 랜덤 문자열
     */
    private String generateRandomAlphaNumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(RANDOM_CHARS.length());
            sb.append(RANDOM_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
