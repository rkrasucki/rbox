package com.rkrasucki.rbox.utilities.validator;

import com.rkrasucki.rbox.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import static org.junit.Assert.*;
@SpringBootTest
public class UserUpdateProfileValidatorTest {

    private User theUser = new User();
    private BindingResult errors = new BeanPropertyBindingResult(theUser, "user");
    private UserUpdateProfileValidator validator = new UserUpdateProfileValidator();

    @Before
    public void setUp(){

        theUser.setUsername("testUsername");
        theUser.setFirstName("testFirstName");
        theUser.setLastName("testLastName");
        theUser.setEmail("test@email.com");
    }

    @Test
    public void shouldPassValidWhenFirstNameIsNotEmpty() {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");

        assertFalse(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void shouldNotPassValidWhenFirstNameIsEmpty() {
        theUser.setFirstName("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void shouldNotPassValidWhenFirstNameIsWhitespace() {
        theUser.setFirstName("  ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void shouldNotPassValidWhenFirstNameIsNull() {
        theUser.setFirstName(null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("firstName"));
    }

    @Test
    public void shouldPassValidWhenLastNameIsNotEmpty() {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");

        assertFalse(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void shouldNotPassValidWhenLastNameIsEmpty() {
        theUser.setLastName("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void shouldNotPassValidWhenLastNameIsWhitespace() {
        theUser.setLastName("   ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void shouldNotPassValidWhenLastNameIsNull() {
        theUser.setLastName(null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("lastName"));
    }

    @Test
    public void shouldPassValidWhenEmailIsNotEmpty() {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");

        assertFalse(errors.hasFieldErrors("email"));
    }

    @Test
    public void shouldNotPassValidWhenEmailIsEmpty() {
        theUser.setEmail("");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void shouldNotPassValidWhenEmailIsWhitespace() {
        theUser.setEmail("   ");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void shouldNotPassValidWhenEmailIsNull() {
        theUser.setEmail(null);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "EMPTY_OR_WHITESPACE");

        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void shouldPassValidWhenEmailGoodSyntax() {
        validator.validate(theUser, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidWhenEmailBadSyntax1() {
        theUser.setEmail("@email.com");
        validator.validate(theUser, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidWhenEmailBadSyntax2() {
        theUser.setEmail("testemail.com");
        validator.validate(theUser, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidWhenEmailBadSyntax3() {
        theUser.setEmail("test@emailcom");
        validator.validate(theUser, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void shouldNotPassValidWhenEmailBadSyntax5() {
        theUser.setEmail("test@email.");
        validator.validate(theUser, errors);

        assertEquals(1, errors.getErrorCount());
    }

}