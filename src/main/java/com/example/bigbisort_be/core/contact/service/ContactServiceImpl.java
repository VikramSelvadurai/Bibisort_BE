package com.example.bigbisort_be.core.contact.service;

import com.example.bigbisort_be.core.contact.request.ContactRequestBean;
import com.example.bigbisort_be.core.contact.response.ContactResponseBean;
import com.example.bigbisort_be.core.contact.utils.ContactUtils;
import com.example.bigbisort_be.persistence.contact.model.repository.ContactRepositoryService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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


    public static final String ENQUIRY_MAIL = "EnquiryReplayMail";

    public static final String ENCODED_PNG = "data:image/png;base64, ";
    public static final String UTF_8 = "UTF-8";
    public static final String MESSAGE = "message";
    public static final String NOTIFICATION_SUBJECT = "Enquiry Confirmation:";
    public static final String ERROR_LOG_TEMPLATE = "Exception occured :{}";

    public static final String CURRENT_ADMIN="currentAdmin";
    public static final String CURRENT_USER ="currentUser";
    public static final String CURRENT_LOGO="currentLogo";

    private final static String EMAIL_HOST = "smtp.gmail.com";
    private final static String EMAIL_PORT = "465";
    private final static String HOST_MAIL = "bigbisort@gmail.com";
    private final static String HOST_EMAIL_PASSWORD = "qrjxpldyihitblch";

    private final ContactRepositoryService contactRepositoryService;
    private final ContactUtils contactUtils;

    public ContactResponseBean addContactInfo(ContactRequestBean contactRequestBean) {
        sendContactInfoToMail(contactRequestBean);
        return contactRepositoryService.addContactInfo(contactRequestBean);
    }

    private JavaMailSenderImpl setProperties(JavaMailSenderImpl mailSender) {
        Properties props = mailSender.getSession().getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.timeout", 30000);
        props.put("mail.debug", true);
        props.put("mail.smtp.connectiontimeout", 10000);
        props.put("mail.smtp.writetimeout", 30000);
        mailSender.getSession().getProperties().putAll(props);
        return mailSender;
    }

    public void sendContactInfoToMail(ContactRequestBean contactRequestBean) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EMAIL_HOST);
        mailSender.setPort(Integer.parseInt(EMAIL_PORT));
        mailSender.setUsername(HOST_MAIL);
        mailSender.setPassword(HOST_EMAIL_PASSWORD);

        setProperties(mailSender);

            try {
                Context context = new Context();
                context.setVariable(CURRENT_USER, contactRequestBean.getEmail());
                context.setVariable(MESSAGE, contactRequestBean.getMessage());
                String data = fetchLogo();
//                System.out.println(" Got the image as string *** ( "+ data +" )");
                context.setVariable(CURRENT_LOGO, ENCODED_PNG + data);
                context.setVariable(
                        CURRENT_ADMIN, "Bibisort");
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper =
                        new MimeMessageHelper(mimeMessage, true, UTF_8); // true = multipart
                mimeMessageHelper.setSubject(NOTIFICATION_SUBJECT);
                mimeMessageHelper.setFrom(HOST_MAIL);
                    mimeMessageHelper.setTo(contactRequestBean.getEmail());
                SpringTemplateEngine templateEngine = new SpringTemplateEngine();
                templateEngine.addTemplateResolver(htmlTemplateResolver());
                final String htmlContent =
                        templateEngine.process(ENQUIRY_MAIL, context);
                mimeMessageHelper.setText(htmlContent, true); // true = isHtml
                mailSender.send(mimeMessage);
            } catch (Exception e) {
                log.error(ERROR_LOG_TEMPLATE, e);
            }
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
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("updated_logo.png")) {
            return contactUtils.encodeBase64(IOUtils.toByteArray(in));
        } catch (Exception e) {
            log.error(ERROR_LOG_TEMPLATE, e.getMessage());
        }
        return null;
    }
}
