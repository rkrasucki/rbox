package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.service.UserService;
import com.rkrasucki.rbox.utilities.validator.UserRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by rkrasucki 26.05.19
 */
@Controller
public class RegisterController {

    private UserService userService;
    private MessageSource messageSource;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public RegisterController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model theModel) {
        theModel.addAttribute("user", new User());
        return "registration-form";

    }

    @PostMapping("/adduser")
    public String processAddUser(@ModelAttribute("user") User theUser,
                                 BindingResult result, Model theModel, Locale locale) {

        String username = theUser.getUsername();
        String userEmail = theUser.getEmail();
        logger.info("Processing registration user for: " + username);

        User userExist = userService.findByUsername(theUser.getUsername());
        User userEmailExist = userService.findByEmail(theUser.getEmail());


        new UserRegisterValidator().validate(theUser, result);

        if(result.hasErrors()) {
            return "registration-form";
        }

        if(userExist != null) {
            logger.info("Processing registration user: " + username + " is already Exist.");
            result.rejectValue("username",
                    messageSource.getMessage("error.registrationPage.username.isAlreadyExist", null, locale));
            theModel.addAttribute("user", new User());
            return "registration-form";

        }

        if(userEmailExist !=null) {
            logger.info("Processing registration user email: " + userEmail + " is already Exist.");
            result.rejectValue("email",
                    messageSource.getMessage("error.registrationPage.email.isAlreadyExist", null, locale));
            theModel.addAttribute("user", new User());
            return "registration-form";
        }

        userService.saveUser(theUser);
        logger.info("Successfully created user: " + username);
        return "registration-confirmation";
    }
}
