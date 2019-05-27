package com.rkrasucki.rbox.utilities.validator;


import com.rkrasucki.rbox.Model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by rkrasucki 26.05.19
 */

public class UserRegisterValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User u = (User) obj;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.registrationPage.username.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.registrationPage.firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.registrationPage.lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.registrationPage.email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.registrationPage.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "error.registrationPage.passwordConfirm.empty");

        isEmailValid(errors, u);

        isPasswordValid(errors, u);

    }


    private void isPasswordValid(Errors errors, User u) {
        if(u.getPassword() !=null) {
            boolean isMatch = PasswordValidator.isValid(u.getPassword());
            if(!isMatch) {
                errors.rejectValue("password", "error.registrationPage.password.incorrectSyntax");
            }
        }
    }

    private void isEmailValid(Errors errors, User u) {
        if(u.getEmail() != null) {
            boolean isMatch = EmailValidator.isValid(u.getEmail());
            if(!isMatch) {
                errors.rejectValue("email", "error.registrationPage.email.incorrectSyntax");
            }
        }
    }
}
