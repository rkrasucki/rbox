package com.rkrasucki.rbox.controller;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;
import com.rkrasucki.rbox.service.UserService;
import com.rkrasucki.rbox.utilities.UserUtilities;
import com.rkrasucki.rbox.utilities.validator.ChangePasswordValidator;
import com.rkrasucki.rbox.utilities.validator.UserUpdateProfileValidator;
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

        UserDto userDto = new UserDto();
        userDto.setUsername(theUser.getUsername());
        userDto.setFirstName(theUser.getFirstName());
        userDto.setLastName(theUser.getLastName());
        userDto.setEmail(theUser.getEmail());
        userDto.setActive(theUser.getActive());
        userDto.setRoleNumber(roleNumber);


        theModel.addAttribute("user", userDto);

        return "profile";
    }

    @GetMapping("/changepass")
    public String changePassword(Model theModel) {
        String username = UserUtilities.getLoggedUser();
        User theUser = userService.findByUsername(username);

        UserDto userDto = new UserDto();
        userDto.setUsername(theUser.getUsername());

        theModel.addAttribute("user", userDto);
        return "change-password";
    }


    @PostMapping("/updatepass")
    public String updateUserPassword(
            @ModelAttribute("user") UserDto userDto,
            BindingResult result, Model theModel, Locale locale) {

        String oldPassword = userDto.getPassword();
        String newPassword = userDto.getNewPassword();
        String username = UserUtilities.getLoggedUser();
        User theUser= userService.findByUsername(username);



        boolean isOldPasswordMatch = userService.checkIfValidOldPassword(theUser, oldPassword);


        if(!isOldPasswordMatch) {
            String errorMessage = messageSource.getMessage("error.profile.oldPasswordIsNotMatch", null, locale);
            String errorMessageInvalid = messageSource.getMessage("error.profile.oldPasswordIsNotMatch", null, locale);
            theModel.addAttribute("invalid", errorMessageInvalid);
            theModel.addAttribute("passwordIsNotMatch", errorMessage);
            theModel.addAttribute(userDto);
            return "change-password";
        }

        new ChangePasswordValidator().validate(userDto, result);
        new ChangePasswordValidator().checkNewPasswordIsMatch(newPassword, result);

        if(result.hasErrors()) {
            return "change-password";
        }
        else {
            userService.updateUserPassword(username, newPassword);
            logger.info("Password successful changed for user: " + username);
            String successMessage = messageSource.getMessage("profile.information.success", null, locale);

            userDto.setUsername(theUser.getUsername());
            userDto.setFirstName(theUser.getFirstName());
            userDto.setLastName(theUser.getLastName());
            userDto.setEmail(theUser.getEmail());
            userDto.setActive(theUser.getActive());
            userDto.setRoleNumber(theUser.getRoleNumber());

            theModel.addAttribute(userDto);
            theModel.addAttribute("success", successMessage);
            return "profile";
        }

    }

    @GetMapping("/editprofile")
    public String showEditProfilePage(Model theModel) {
        String username = UserUtilities.getLoggedUser();
        User theUser = userService.findByUsername(username);

        UserDto userDto = new UserDto();
        userDto.setUsername(theUser.getUsername());
        userDto.setFirstName(theUser.getFirstName());
        userDto.setLastName(theUser.getLastName());
        userDto.setEmail(theUser.getEmail());

        theModel.addAttribute("user", userDto);
        return "profile-edit";
    }

    @PostMapping("/updateprofile")
    public String updateUserProfile(@ModelAttribute("user") UserDto userDto,
                                    BindingResult result, Model theModel, Locale locale) {

        String username = UserUtilities.getLoggedUser();

        new UserUpdateProfileValidator().validate(userDto, result);

        if (result.hasErrors()) {
            return "profile-edit";
        }

        if(username != null){
            userService.updateUserProfile(userDto, username);
            logger.info("User profile saved successfully fo user: " + username);
            String successMessage = messageSource.getMessage("profile.information.success", null, locale);
        }
        return "profile";
    }
}
