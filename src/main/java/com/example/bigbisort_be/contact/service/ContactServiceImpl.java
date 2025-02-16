package com.example.bigbisort_be.contact.service;

import com.example.bigbisort_be.contact.request.ContactRequestBean;
import com.example.bigbisort_be.contact.response.ContactResponseBean;
import com.example.bigbisort_be.contact.utils.ContactUtils;
import com.example.bigbisort_be.persistence.contact.model.repository.ContactRepositoryService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.InputStream;
import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl {

    public static final String SIMPLE_MESSAGE = "simpleMessage";
    public static final String NOTIFICATION_ALERT_EMAIL_TEMPLATE = "NotificationAlertsEmail";
    public static final String REPLY_SUMMARY_EMAIL_TEMPLATE = "ReplySummaryEmail";
    public static final String ENCODED_PNG = "data:image/png;base64, ";
    public static final String UTF_8 = "UTF-8";
    public static final String MESSAGE = "message";
    public static final String RESET_KEY_PARAM = "resetKey";
    public static final String BCC = "bcc";

    public static final String NOTIFICATION_SUBJECT = "Notification";
    public static final String ERROR_LOG_TEMPLATE = "Exception occured :{}";

    public static final String CURRENT_ADMIN="currentAdmin";
    public static final String CURRENT_USER ="currentUser";
    public static final String CURRENT_LOGO="currentLogo";
    public static final String BIBISORT_ADMIN="Bigbisort Admin";

    private final static String EMAIL_HOST = "smtp.office365.com";
    private final static String EMAIL_PORT = "587";
    private final static String EMAIL_USERNAME = "vikram.selvadurai@gmail.com";
    private final static String TO_MAIL_ID = "bigbisort@gmail.com ";
    private final static String EMAIL_PASSWORD = "dkBwc3N2NTIxOTk4";

    private final ContactRepositoryService contactRepositoryService;
    private final ContactUtils contactUtils;

    public ContactResponseBean addContactInfo(ContactRequestBean contactRequestBean) {
//        sendContactInfoToMail(contactRequestBean);
        return contactRepositoryService.addContactInfo(contactRequestBean);
    }

    public JavaMailSender getJavaMailSender() {
        byte[] decodedBytes = Base64.getDecoder().decode(EMAIL_PASSWORD);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        if (!Objects.isNull(emailServerEntity)) {
            mailSender.setHost(EMAIL_HOST);
            mailSender.setPort(Integer.parseInt(EMAIL_PORT));
            mailSender.setUsername(EMAIL_USERNAME);
            mailSender.setPassword(new String(decodedBytes));
            setProperties(mailSender);
//        } else {
//            throw new SMTPException(
//                    translator.toLocale(MESSAGE_SYSTEM_EMAIL_CONFIG_STATUS_NOT_CONFIGURED));
//        }

        return mailSender;
    }
    private JavaMailSenderImpl setProperties(JavaMailSenderImpl mailSender) {
        Properties properties = mailSender.getSession().getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.debug", true);
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.timeout", 30000);
        properties.put("mail.smtp.connectiontimeout", 10000);
        properties.put("mail.smtp.writetimeout", 30000);
        mailSender.getSession().getProperties().putAll(properties);
        return mailSender;
    }

    public void sendContactInfoToMail(ContactRequestBean contactRequestBean) {
//            EmailServerEntity emailServerEntity = getEmailServerEntity();
            JavaMailSender mailSender = getJavaMailSender();
            try {
                Context context = new Context();
                context.setVariable(CURRENT_USER, contactRequestBean.getEmail());
                context.setVariable(MESSAGE, "message");
//                context.setVariable(BCC, true);
                String data = fetchLogo();
                context.setVariable(CURRENT_LOGO, ENCODED_PNG + data);
                context.setVariable(
                        CURRENT_ADMIN, "Bibisort Admin user");
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper =
                        new MimeMessageHelper(mimeMessage, true, UTF_8); // true = multipart
                mimeMessageHelper.setSubject(NOTIFICATION_SUBJECT);
                mimeMessageHelper.setFrom(EMAIL_USERNAME);
//                if (bcc) {
//                    mimeMessageHelper.setBcc();
//                } else {
                    mimeMessageHelper.setTo(TO_MAIL_ID);
//                }
                SpringTemplateEngine templateEngine = new SpringTemplateEngine();
                templateEngine.addTemplateResolver(htmlTemplateResolver());
                final String htmlContent =
                        templateEngine.process(NOTIFICATION_ALERT_EMAIL_TEMPLATE, context);
                mimeMessageHelper.setText(htmlContent, true); // true = isHtml
                mailSender.send(mimeMessage);
            } catch (Exception e) {
                log.error(ERROR_LOG_TEMPLATE, e);
            }




//            JavaMailSender mailSender = getJavaMailSender();
//            Context context = new Context();
//            context.setVariable(CURRENT_USER, "Bigbisort");
//            context.setVariable(MESSAGE,"message from Bigbisort");
//            String data = fetchLogo();
//            context.setVariable(CURRENT_LOGO, ENCODED_PNG + data);
//            context.setVariable(
//                    CURRENT_ADMIN,  BIBISORT_ADMIN);
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper =
//                    new MimeMessageHelper(mimeMessage, true, UTF_8); // true = multipart
//            Instant yesterdayInstant = Instant.now().minus(Period.ofDays(1));
//            mimeMessageHelper.setSubject("Confirmation Mail Submission");
////                    .format(Date.from(yesterdayInstant)));
//            mimeMessageHelper.setFrom(EMAIL_USERNAME);
//            mimeMessageHelper.setTo(TO_MAIL_ID);
////            if(Objects.nonNull(attachmentFile)) {
////                mimeMessageHelper.addAttachment(attachmentFile.getName(), attachmentFile);
////            }
//            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//            templateEngine.addTemplateResolver(htmlTemplateResolver());
//            final String htmlContent =
//                    templateEngine.process(REPLY_SUMMARY_EMAIL_TEMPLATE, context);
//            mimeMessageHelper.setText(htmlContent, true); // true = isHtml
//            mailSender.send(mimeMessage);
//        } catch (Exception e) {
//            log.error(ERROR_LOG_TEMPLATE, e);
//        }
    }

    public ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setPrefix("email/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    public String fetchLogo() {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("mongo_front.png")) {
            return contactUtils.encodeBase64(IOUtils.toByteArray(in));
        } catch (Exception e) {
            log.error(ERROR_LOG_TEMPLATE, e.getMessage());
        }
        return null;
    }
}
