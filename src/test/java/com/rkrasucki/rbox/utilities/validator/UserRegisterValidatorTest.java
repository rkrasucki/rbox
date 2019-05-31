package com.rkrasucki.rbox.utilities.validator;

import com.rkrasucki.rbox.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static org.junit.Assert.*;
@SpringBootTest
public class UserRegisterValidatorTest {

    private User theUser = new User();
    private Errors errors = new BeanPropertyBindingResult(theUser, "theUser");
    private UserRegisterValidator validator = new UserRegisterValidator();

    @Before
    public void setUpUser() {
        theUser.setUsername("test_username");
        theUser.setFirstName("test_firstName");
        theUser.setLastName("test_lastName");
        theUser.setPassword("A1test_pass");
        theUser.setPasswordConfirm("A1test_pass");
        theUser.setEmail("test@email.com");
        theUser.setId(1);
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace() {
        theUser.setUsername(null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("username"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace2() {
        theUser.setUsername(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("username"));
    }
    @Test
    public void testValidationUtilsEmptyOrWhitespace3() {
        theUser.setUsername("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("username"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace5() {
        theUser.setFirstName("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace6() {
        theUser.setFirstName(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace7() {
        theUser.setFirstName("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace8() {
        theUser.setLastName("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace9() {
        theUser.setLastName(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace10() {
        theUser.setLastName("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace11() {
        theUser.setEmail("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace12() {
        theUser.setEmail(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace13() {
        theUser.setEmail("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("email"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace14() {
        theUser.setPassword("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace15() {
        theUser.setPassword(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace16() {
        theUser.setPassword("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("password"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace17() {
        theUser.setPasswordConfirm("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("passwordConfirm"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace18() {
        theUser.setPasswordConfirm(" ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "EMPTY_OR_WHITESPACE");
        assertTrue(errors.hasFieldErrors("passwordConfirm"));
    }

    @Test
    public void testValidationUtilsEmptyOrWhitespace19() {
        theUser.setPasswordConfirm("test");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "EMPTY_OR_WHITESPACE");
        assertFalse(errors.hasFieldErrors("passwordConfirm"));
    }

    @Test public void testValidateForEmailsInput1() {
        theUser.setEmail("@example.com");
        validator.validate(theUser,errors);
        assertEquals(1,errors.getErrorCount());
    }

    @Test public void testValidateForEmailsInput2() {
        theUser.setEmail("@example.com");
        validator.validate(theUser,errors);
        assertEquals(1,errors.getErrorCount());
    }

    @Test
    public void testValidateForPasswordInput1() {
        theUser.setPassword("badPass");
        validator.validate(theUser, errors);
        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testValidateForPasswordInput2() {
        theUser.setPassword("GoodPass_123");
        validator.validate(theUser, errors);
        assertEquals(0, errors.getErrorCount());
    }


}