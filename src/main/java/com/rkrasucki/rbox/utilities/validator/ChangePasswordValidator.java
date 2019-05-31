package com.rkrasucki.rbox.utilities.validator;


import com.rkrasucki.rbox.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by rkrasucki 29.05.19
 */

public class ChangePasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "error.registrationPage.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "error.registrationPage.password.empty");
    }

    public void checkNewPasswordIsMatch(String newPassword, Errors errors) {
        if (newPassword != null) {
            boolean isMatch = PasswordValidator.isValid(newPassword);
            if (!isMatch) {
                errors.rejectValue("newPassword", "error.profile.newPasswordIsNotMatch");
            }
        }
    }
}

