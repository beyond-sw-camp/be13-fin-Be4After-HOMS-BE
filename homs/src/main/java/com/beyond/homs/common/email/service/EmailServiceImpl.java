package com.beyond.homs.common.email.service;

import com.beyond.homs.common.email.data.EmailTypeEnum;
import com.beyond.homs.common.email.entity.EmailMessage;
import com.beyond.homs.order.entity.Order;
import com.beyond.homs.order.repository.OrderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final OrderRepository orderRepository;

    @Override
    public String sendMail(EmailMessage emailMessage, EmailTypeEnum type) { // EmailTypeEnum을 type으로 받음
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        System.out.println(emailMessage.getTo());

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Long id = emailMessage.getId(); // 식별자 id
            Order order = null;
            if (id != null) {
                order = orderRepository.findByOrderId(id).getFirst();
                // 주문에서 유저의 이메일을 찾아서 반환
                mimeMessageHelper.setTo(order.getUser().getManagerEmail());
            }

            if(emailMessage.getTo() != null){
                mimeMessageHelper.setTo(emailMessage.getTo());      // 메일 수신자
            } else{
                switch (type){
                    case ORDER_CONFIRMATION:
                    case ORDER_CANCELLATION:
                        emailMessage.setSubject(order.getOrderCode()); // 주문번호 반환
                        break;
                }
            }

            
            // 이미지 가져와서 할당
            ClassPathResource classPathResource = new ClassPathResource("templates/homsLogo.png");
            mimeMessageHelper.addInline("homsLogo", classPathResource);

            // EmailTypeEnum에 따라 동적 내용과 변수를 구성
            Map<String, Object> templateVariables = new HashMap<>();
            String templateName = "email-base-template"; // 기본 템플릿 이름
            String mailSubject = ""; // 메일 제목을 저장할 변수 초기화

            String subject = "";
            String content = "";
            String contentHtml = "";

            switch (type) {
                case ORDER_CONFIRMATION:
                    // 주문 승인 메일
                    subject = emailMessage.getSubject(); // 배송코드
                    contentHtml =
                            "<p style=\"font-size: 16px; color: #333333; margin-bottom: 15px;\">" +
                            "고객님, 요청하신 주문(<strong>"+subject+"</strong>)이 <strong>승인</strong>되었습니다.";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", contentHtml);
                    mailSubject = "[HOMS] 주문이 정상적으로 승인되었습니다.";
                    break;

                case ORDER_CANCELLATION:
                    // 주문 거부 메일
                    subject = emailMessage.getSubject(); // 배송코드
                    content = emailMessage.getContent(); // 거부 사유 텍스트
                    contentHtml =
                            "<p style=\"font-size: 16px; color: #333333; margin-bottom: 15px;\">" +
                            "고객님, 요청하신 주문(<strong>"+subject+"</strong>)이 <strong>거부</strong>되었습니다." +
                            "</p>" +
                            "<p style=\"font-size: 15px; color: #555555; margin-bottom: 25px;\">" +
                            "자세한 사유는 아래를 확인해 주시기 바랍니다." +
                            "</p>" +
                            "<div class=\"reason-box\">" + // 새로운 CSS 클래스 활용
                            "    <h4 style=\"font-weight: 600; color: #333333;\">상세 거부 사유</h4>" +
                            "    <p style=\"white-space: pre-wrap; word-break: break-word;\">" + content + "</p>" + // 텍스트 줄바꿈 및 긴 단어 처리
                            "</div>" +
                            "<p style=\"font-size: 15px; color: #555555; margin-top: 30px;\">" +
                            "문의사항이 있으시면 고객센터로 연락 주시기 바랍니다." +
                            "</p>";

                    templateVariables.put("mainTitle", "주문 처리 결과: 주문 거부 안내");
                    templateVariables.put("dynamicContent", contentHtml);
                    mailSubject = "[HOMS] 주문 거부 안내 메일입니다.";
                    break;

                case SHIPPING_UPDATE:
                    // 배송 상태 알림
                    content = emailMessage.getContent();
                    contentHtml = "<p> 배송 상태가 " + content + "으로 변경되었습니다. </p>" +
                                            "<p> HOMS를 이용해주셔 감사합니다. </p>";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", contentHtml);
                    mailSubject = "[HOMS] 배송 상태가 변경되었습니다.";
                    break;

                case ACCOUNT_CREATED:
                    // 가입 환영 이메일 내용
                    subject = emailMessage.getSubject(); // 아이디
                    content = emailMessage.getContent(); // 비밀번호
                    contentHtml =
                            "<p style=\"font-size: 16px; color: #333333; margin-bottom: 15px;\">" +
                                    "고객님, 저희 HOMS에 가입해주셔서 진심으로 감사드립니다!" +
                                    "</p>" +
                                    "<p style=\"font-size: 15px; color: #555555; margin-bottom: 25px;\">" +
                                    "자세한 계정 정보는 아래를 확인해 주시기 바랍니다." +
                                    "</p>" +
                                    "<div class=\"reason-box\">" + // 새로운 CSS 클래스 활용
                                    "    <h4 style=\"font-weight: 600; color: #333333;\">아이디</h4>" +
                                    "    <p style=\"white-space: pre-wrap; word-break: break-word;\">" + subject + "</p>" + // 텍스트 줄바꿈 및 긴 단어 처리
                                    "    <h4 style=\"font-weight: 600; color: #333333;\">비밀번호</h4>" +
                                    "    <p style=\"white-space: pre-wrap; word-break: break-word;\">" + content + "</p>" + // 텍스트 줄바꿈 및 긴 단어 처리
                                    "</div>" +
                                    "<p style=\"font-size: 15px; color: #555555; margin-top: 30px;\">" +
                                    "지금 바로 서비스를 이용해보세요." +
                                    "</p>";

                    templateVariables.put("mainTitle", "HOMS에 오신 것을 환영합니다!");
                    templateVariables.put("dynamicContent", contentHtml);
                    mailSubject = "[HOMS] HOMS에 오신 것을 환영합니다!";
                    break;
                
                case SETTLE_STATUS:
                    // 정산 상태 알림
                    contentHtml =
                            "<p style=\"font-size: 16px; color: #333333; margin-bottom: 15px;\">" +
                                    "고객님, 요청하신 주문의 정산이 <strong>완료</strong>되었습니다.";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", contentHtml);
                    mailSubject = "[HOMS] 정산 상태가 변경되었습니다.";
                    break;

                default:
                    // 정의되지 않은 유형 처리 또는 기본 내용
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", "<p>요청하신 메일 내용입니다.</p>");
                    mailSubject = "[HOMS] 알림 메일입니다.";
                    break;
            }

            mimeMessageHelper.setSubject(mailSubject); // 메일 제목 지정

            // Thymeleaf 템플릿에 데이터 주입
            mimeMessageHelper.setText(processEmailTemplate(templateName, templateVariables), true);
            javaMailSender.send(mimeMessage); // 실제 이메일 전송
            log.info("Email sent successfully to: {} with type: {}", emailMessage.getTo(), type);
            return null;

        } catch (MessagingException e) {
            log.error("Failed to send email to: {} with type: {}", emailMessage.getTo(), type, e);
            throw new RuntimeException("Email sending failed", e);
        }
    }

    // Thymeleaf를 통한 HTML 적용
    private String processEmailTemplate(String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables); // 전달받은 모든 변수들을 Context에 추가
        return templateEngine.process(templateName, context);
    }
}