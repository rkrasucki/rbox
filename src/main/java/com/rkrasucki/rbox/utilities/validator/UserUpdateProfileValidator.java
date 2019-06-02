package com.rkrasucki.rbox.utilities.validator;

import com.rkrasucki.rbox.model.User;
import com.rkrasucki.rbox.model.UserDto;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by rkrasucki 31.05.19
 */

public class UserUpdateProfileValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserDto u = (UserDto) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.profileEdit.emptyField");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.profileEdit.emptyField");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.profileEdit.emptyField");

        isEmailValid(errors, u);
    }

    private void isEmailValid(Errors errors, UserDto u) {
        if(u.getEmail() != null) {
            boolean isMatch = EmailValidator.isValid(u.getEmail());
            if(!isMatch) {
                errors.rejectValue("email", "error.registrationPage.email.incorrectSyntax");
            }
        }
    }
}
