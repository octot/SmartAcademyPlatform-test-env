package com.authentication.Authenitication.AuthenticationModule.service;

import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Slf4j
@Service
public class EmailService {

    private final TemplateEngine templateEngine;



    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public EmailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Async("taskExecutor")
    public void sendEmailOtp(String toEmail, String otp, long expiryTime) {
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("otpExpireTime", expiryTime);
        String htmlContent = templateEngine.process("otp-email", context);
        sendEmail(toEmail, "SmartPay OTP Verification", htmlContent);
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            Email from = new Email(fromEmail);
            Email to = new Email(toEmail);
            Content content = new Content("text/html", body);
            Mail mail = new Mail(from, subject, to, content);
            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(com.sendgrid.Method.valueOf(Method.POST));
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
        } catch (IOException e) {
            log.error("Email sending failed for {}", toEmail, e);

        }

    }




}
