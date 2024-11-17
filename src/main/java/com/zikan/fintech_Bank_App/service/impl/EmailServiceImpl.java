package com.zikan.fintech_Bank_App.service.impl;

import com.zikan.fintech_Bank_App.EmailTemplate;
import com.zikan.fintech_Bank_App.dto.EmailDetails;
import com.zikan.fintech_Bank_App.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final SpringTemplateEngine templateEngine;



    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderEmail);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            System.out.println("Mail successfully sent");
            log.info("Message sent to: {}", emailDetails.getRecipient());
            log.info("Message sender: {}", senderEmail);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setText(emailDetails.getMessageBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());

            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment().toString()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            javaMailSender.send(mimeMessage);

            log.info(file.getFilename() + " has been sent to user with email " + emailDetails.getRecipient());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @Async
    public void sendEmailWithAttachment(EmailDetails emailDetails, EmailTemplate template) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            emailDetails.getProps().forEach(context::setVariable);
            String html = templateEngine.process(template.getDescription(), context);
            helper.setTo(emailDetails.getRecipient());
            helper.setText(html, true);
            helper.setSubject(emailDetails.getSubject());
            helper.setFrom(emailDetails.getFrom());

            log.info("Got to java email sender - subject is {} " + emailDetails.getSubject());

            javaMailSender.send(message);

        } catch (MessagingException e) {
            log.info("Error {}" + e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }

    }
}
