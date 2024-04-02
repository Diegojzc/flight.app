package com.tokioschool.flight.app.email.service.impl;

import com.tokioschool.flight.app.email.dto.EmailDTO;
import com.tokioschool.flight.app.email.service.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class emailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Override
    public void sendEmail(EmailDTO emailDTO) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            mimeMessage.addRecipients(Message.RecipientType.TO, emailDTO.getTo());
            mimeMessage.setSubject(emailDTO.getSubject(), StandardCharsets.UTF_8.name());


        }catch (MessagingException e){
            log.error("Exception when building mimeMessage, to:{} sbject:{}",
                    emailDTO.getTo(),
                    emailDTO.getSubject(), e);
            return;
        }
    }
}
