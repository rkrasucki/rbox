package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.service.UserService;
import com.rkrasucki.rbox.utilities.registration.OnRegistrationCompleteEvent;
import com.rkrasucki.rbox.utilities.validator.UserRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by rkrasucki 26.05.19
 */
@Controller
public class RegisterController {

    private UserService userService;
    private MessageSource messageSource;
    private JavaMailSender mailSender;
    private ApplicationEventPublisher eventPublisher;
    private Logger logger = Logger.getLogger(getClass().getName());



    @Autowired
    public RegisterController(UserService userService, MessageSource messageSource, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model theModel) {
        theModel.addAttribute("user", new User());
        return "registration-form";

    }

    @PostMapping("/adduser")
    public String processAddUser(@ModelAttribute("user") User theUser,
                                 BindingResult result, Model theModel,
                                 Locale locale, HttpServletRequest request) throws RoleNotFoundException {

        String username = theUser.getUsername();
        String userEmail = theUser.getEmail();
        logger.info("Processing registration user for: " + username);

        User userExist = userService.findByUsername(theUser.getUsername());
        User userEmailExist = userService.findByEmail(theUser.getEmail());

        new UserRegisterValidator().validate(theUser, result);

        if (result.hasErrors()) {
            return "registration-form";
        }

        if (userExist != null) {
            logger.info("Processing registration user: " + username + " is already Exist.");
            String errorMessage = messageSource.getMessage("error.registrationPage.username.isAlreadyExist", null, locale);
            theModel.addAttribute("user", theUser);
            theModel.addAttribute("registrationError", errorMessage);
            return "registration-form";
        }

        if (userEmailExist != null) {
            logger.info("Processing registration user email: " + userEmail + " is already Exist.");
            String errorMessage = messageSource.getMessage("error.registrationPage.email.isAlreadyExist", null, locale);
            theModel.addAttribute("user", theUser);
            theModel.addAttribute("registrationError", errorMessage);
            return "registration-form";
        }

        User registered = userService.registerNewUserAccount(theUser);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(getAppUrl(request), locale, registered));

        logger.info("Successfully created user: " + username);
        theModel.addAttribute("message", messageSource.getMessage("register.confirm.message.verificationMail", null, locale));
        return "registration-confirmation";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model theModel, @RequestParam("token") String token, Locale locale) {

        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            theModel.addAttribute("message", messageSource.getMessage("register.confirm.message.accountVerified", null, locale));
            return "registration-confirmation";
        }
        else {
            theModel.addAttribute("message", messageSource.getMessage("register.confirm.message.accountVerifiedFail", null, locale));
            }
            return "registration-confirmation";
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

