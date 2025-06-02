package com.beyond.homs.notify.email.service;

import com.beyond.homs.notify.email.data.EmailTypeEnum;
import com.beyond.homs.notify.email.entity.EmailMessage;
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
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public String sendMail(EmailMessage emailMessage, EmailTypeEnum type) { // EmailTypeEnum을 type으로 받음
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());      // 메일 수신자
            mimeMessageHelper.setSubject(emailMessage.getSubject()); // 메일 제목
            
            // 이미지 가져와서 할당
            ClassPathResource classPathResource = new ClassPathResource("templates/homsLogo.png");
            mimeMessageHelper.addInline("homsLogo", classPathResource);

            // EmailTypeEnum에 따라 동적 내용과 변수를 구성
            Map<String, Object> templateVariables = new HashMap<>();
            String templateName = "email-base-template"; // 기본 템플릿 이름

            switch (type) {
                // case VERIFICATION:
                //     // 회원가입 인증 이메일 내용
                //     String verificationCode = emailMessage.getContent(); // EmailMessage의 content를 인증 코드로 사용
                //     String verificationHtml = "<p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>" +
                //                                 "<br>" +
                //                                 "<div align=\"center\" style=\"border:1px solid black; font-family:verdana;\">" +
                //                                 "    <h3 style=\"color:blue\"> 회원가입 인증 코드 입니다. </h3>" +
                //                                 "    <div style=\"font-size:130%\">" + verificationCode + "</div>" +
                //                                 "</div>";
                //     templateVariables.put("mainTitle", "안녕하세요. HOMS 회원가입 인증 메일입니다.");
                //     templateVariables.put("dynamicContent", verificationHtml);
                //     // templateName은 그대로 "email-base-template" 사용
                //     break;

                case ORDER_CONFIRMATION:
                    // 주문 승인 메일
                    String contentHtml = "<p> 요청하신 주문이 승인되었습니다! </p>" +
                                         "<p> HOMS를 이용해주셔 감사합니다. </p>";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", contentHtml);
                    break;

                case ORDER_CANCELLATION:
                    // 주문 거부 메일
                    String rejectReason = emailMessage.getContent();
                    String rejectHtml = "<p> 요청하신 주문이 거부되었습니다! </p>" +
                                        "<div align=\"center\" style=\"border:1px solid black; font-family:verdana;\">" +
                                        "    <h3 style=\"color:blue\"> 상세 사유 </h3>" +
                                        "    <div>" + rejectReason + "</div>" +
                                        "</div>" +
                                        "<p> HOMS를 이용해주셔 감사합니다. </p>";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", rejectHtml);
                    break;

                case SHIPPING_UPDATE:
                    // 배송 상태 알림
                    String orderStatus = emailMessage.getContent();
                    String orderStatusHtml = "<p> 배송상태가 " + orderStatus + "으로 변경되었습니다. </p>" +
                                            "<p> HOMS를 이용해주셔 감사합니다. </p>";
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", orderStatusHtml);
                    break;

                case ACCOUNT_CREATED:
                    // 가입 환영 이메일 내용
                    String welcomeHtml = "<p> 저희 HOMS에 가입해주셔서 진심으로 감사드립니다!</p>" +
                                         "<p> 지금 바로 서비스를 이용해보세요.</p>";
                    templateVariables.put("mainTitle", "HOMS에 오신 것을 환영합니다!");
                    templateVariables.put("dynamicContent", welcomeHtml);
                    break;

                default:
                    // 정의되지 않은 유형 처리 또는 기본 내용
                    templateVariables.put("mainTitle", "HOMS에서 알려드립니다.");
                    templateVariables.put("dynamicContent", "<p>요청하신 메일 내용입니다.</p>");
                    break;
            }

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