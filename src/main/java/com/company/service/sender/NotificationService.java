package com.company.service.sender;

import com.company.dao.api.AccountDAO;
import com.company.model.Account;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@PropertySource(value = "classpath:application.properties", encoding = "windows-1251")
// кодировка windows-1251 для считывания русского текста
public class NotificationService {
    private static final Logger LOGGER = Logger.getLogger(NotificationService.class);

    private final String scheduleSubject;
    private final String scheduleText;
    private final String settingsSubject;
    private final String settingsText;

    private final AccountDAO accountDAO;
    private final MessageSender emailSender;

    public NotificationService(
            MessageSender emailSender,
            @Value("${mail.message.scheduleChanged.subject}") String scheduleSubject,
            @Value("${mail.message.scheduleChanged.text}") String scheduleText,
            @Value("${mail.message.settingsChanged.subject}") String settingsSubject,
            @Value("${mail.message.settingsChanged.text}") String settingsText,
            AccountDAO accountDAO) {

        this.emailSender = emailSender;
        this.scheduleSubject = scheduleSubject;
        this.scheduleText = scheduleText;
        this.settingsSubject = settingsSubject;
        this.settingsText = settingsText;
        this.accountDAO = accountDAO;
    }

    public void sendScheduleNotifications(int groupId) {
        List<Account> accounts = accountDAO.readByGroup(groupId);
        accounts.forEach(s -> {
            if (s.getScheduleNotidication() || s.getEmail() != null || "".equals(s.getEmail())) {
                try {
                    emailSender.send(s.getEmail(), scheduleSubject, scheduleText);
                } catch (Exception e) {
                    LOGGER.warn("Sending message to " + s.getEmail() + " failed.", e);
                }
            }
        });
    }

    public void sendSettingsNotifications(int groupId) {
        List<Account> accounts = accountDAO.readByGroup(groupId);
        accounts.forEach(s -> {
            if (s.getSettingsNotification() || s.getEmail() != null || "".equals(s.getEmail())) {
                try {
                    emailSender.send(s.getEmail(), settingsSubject, settingsText);
                } catch (Exception e) {
                    LOGGER.warn("Sending message to " + s.getEmail() + " failed.", e);
                }
            }
        });
    }
}
