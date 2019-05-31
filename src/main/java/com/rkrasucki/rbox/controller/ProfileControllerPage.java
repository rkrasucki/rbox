package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.service.UserService;
import com.rkrasucki.rbox.utilities.UserUtilities;
import com.rkrasucki.rbox.utilities.validator.ChangePasswordValidator;
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
 * Created by rkrasucki 29.05.19
 */
@Controller
public class ProfileControllerPage {


    private Logger logger = Logger.getLogger(getClass().getName());
    private UserService userService;
    private MessageSource messageSource;

    public ProfileControllerPage(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model theModel) {

        String username = UserUtilities.getLoggedUser();

        User theUser = userService.findByUsername(username);
        int roleNumber = theUser.getRoles().iterator().next().getId();
        theUser.setRoleNumber(roleNumber);
        theModel.addAttribute("user", theUser);

        return "profile";
    }

    @GetMapping("/changepass")
    public String changePassword(Model theModel) {
        String username = UserUtilities.getLoggedUser();
        User theUser = userService.findByUsername(username);
        theModel.addAttribute("user", theUser);
        return "change-password";
    }


    @PostMapping("/updatepass")
    public String updateUserPassword(
            @ModelAttribute("user") User theUser,
            BindingResult result, Model theModel, Locale locale) {

        String oldPassword = theUser.getPassword();
        String newPassword = theUser.getNewPassword();
        String username = UserUtilities.getLoggedUser();
        User user= userService.findByUsername(username);
        boolean isOldPasswordMatch = userService.checkIfValidOldPassword(user, oldPassword);


        if(!isOldPasswordMatch) {
            String errorMessage = messageSource.getMessage("error.profile.oldPasswordIsNotMatch", null, locale);
            String errorMessageInvalid = messageSource.getMessage("error.profile.oldPasswordIsNotMatch", null, locale);
            theModel.addAttribute("invalid", errorMessageInvalid);
            theModel.addAttribute("passwordIsNotMatch", errorMessage);
            theModel.addAttribute(theUser);
            return "change-password";
        }

        new ChangePasswordValidator().validate(theUser, result);
        new ChangePasswordValidator().checkNewPasswordIsMatch(newPassword, result);

        if(result.hasErrors()) {
            return "change-password";
        }
        else {
            userService.updateUserPassword(username, newPassword);
            logger.info("Password successful changed for user: " + username);
            String successMessage = messageSource.getMessage("profile.information.success", null, locale);
            theModel.addAttribute(user);
            theModel.addAttribute("success", successMessage);
            return "profile";
        }

    }
}
