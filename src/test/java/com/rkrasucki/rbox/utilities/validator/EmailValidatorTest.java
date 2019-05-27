package com.rkrasucki.rbox.utilities.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    @Test
    public void isEmail1ValidTrue() {
        String email = "test@mail.com";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail2ValidFalse() {
        String email = "test@mail.";
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail3ValidFalse() {
        String email = "testąęęćć@mail.com";
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail4ValidFalse() {
        String email = "test@mail";
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail5ValidFalse() {
        String email = "@mail.com";
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail6ValidFalse() {
        String email = "!@mail.com";
        assertFalse(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail7ValidTrue() {
        String email = "t_test@mail.com";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail8ValidTrue() {
        String email = "t.test@mail.com";
        assertTrue(EmailValidator.isValid(email));
    }

    @Test
    public void isEmail9ValidTrue() {
        String email = "t.test@mail.test.com";
        assertTrue(EmailValidator.isValid(email));
    }






}