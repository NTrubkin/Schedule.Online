package com.company.service.sender;

import com.company.util.LoginValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailSender implements MessageSender {
    private static final Logger LOGGER = Logger.getLogger(EmailSender.class);

    private final String username;
    private final String password;
    private final Properties properties = new Properties();

    public EmailSender(String username, String password, String propFile) {

        try {
            properties.load(EmailSender.class.getResourceAsStream(propFile));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read properties file " + propFile, e);
        }

        this.username = username;
        this.password = password;
    }

    @Override
    public void send(String toEmail, String subject, String text) {
        if(!new Boolean(properties.getProperty("mail.active"))) {
            LOGGER.info("Property mail.active is false. Skip sending.");
        }

        if(!isAddressValid(toEmail) || !isMessageValid(text)) {
            throw new IllegalArgumentException("Address or message is not valid. Abort sending.");
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setText(text);
            message.setSubject(subject);

            Transport.send(message);

            LOGGER.info("Sending email to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Message sending error", e);
        }
    }

    @Override
    public boolean isAddressValid(String address) {
        return LoginValidator.isEmailValid(address);
    }

    @Override
    public boolean isMessageValid(String message) {
        return true;
    }
}
