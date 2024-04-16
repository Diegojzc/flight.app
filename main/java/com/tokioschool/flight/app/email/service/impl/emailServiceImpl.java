package com.tokioschool.flight.app.email.service.impl;

import com.tokioschool.flight.app.email.dto.EmailDTO;
import com.tokioschool.flight.app.email.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
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
    try {
      mimeMessage.addRecipients(Message.RecipientType.TO, emailDTO.getTo());
      mimeMessage.setSubject(emailDTO.getSubject(), StandardCharsets.UTF_8.name());

      MimeMultipart multipart = new MimeMultipart();
      mimeMessage.setContent(multipart);

      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setText(emailDTO.getTextBody(), StandardCharsets.UTF_8.name());
      multipart.addBodyPart(mimeBodyPart);

      emailDTO
          .getAttachment()
          .forEach(
              attachmentDTO -> {
                try {
                  MimeBodyPart attachmentPart = new MimeBodyPart();
                  attachmentPart.setDataHandler(
                      new DataHandler(
                          new ByteArrayDataSource(
                              attachmentDTO.getContent(), attachmentDTO.getContentType())));

                  attachmentPart.setFileName(attachmentDTO.getFileName());
                  multipart.addBodyPart(attachmentPart);
                } catch (MessagingException e) {
                  log.error(
                      "Exception when adding attachment, will be ignored, to:{} subject:{}",
                      emailDTO.getTo(),
                      emailDTO.getSubject(),
                      e);
                }
              });

    } catch (MessagingException e) {
      log.error(
          "Exception when building mimeMessage, to:{} subject:{}",
          emailDTO.getTo(),
          emailDTO.getSubject(),
          e);
      return;
    }
    try {
      javaMailSender.send(mimeMessage);
    } catch (MailException e) {
      log.error(
          "Exception when sending mimeMessage, to:{} sbject:{}",
          emailDTO.getTo(),
          emailDTO.getSubject(),
          e);
    }
  }
}
