package com.rkrasucki.rbox.utilities.registration.listener;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.service.UserService;
import com.rkrasucki.rbox.utilities.registration.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Created by rkrasucki 06.06.19
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private UserService userService;
    private MessageSource messageSource;
    private JavaMailSender mailSender;
    private Environment env;

    @Autowired
    public RegistrationListener(UserService userService, MessageSource messageSource, JavaMailSender mailSender, Environment env) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.mailSender = mailSender;
        this.env = env;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(OnRegistrationCompleteEvent event, User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messageSource.getMessage("register.titlePage", null, event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
}
