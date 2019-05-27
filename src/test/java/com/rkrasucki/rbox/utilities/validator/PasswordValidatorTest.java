package com.rkrasucki.rbox.utilities.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordValidatorTest {

    @Test
    public void isPasswordValid1True() {
        String pass = "Test_pass123";
        assertTrue(PasswordValidator.isValid(pass));
    }

    @Test
    public void isPasswordValid2False() {
        String pass = "Testpass123";
        assertFalse(PasswordValidator.isValid(pass));
    }

    @Test
    public void isPasswordValid3True() {
        String pass = "Te@1";
        assertTrue(PasswordValidator.isValid(pass));
    }

    @Test
    public void isPasswordValid4False() {
        String pass = "T2";
        assertFalse(PasswordValidator.isValid(pass));
    }

    @Test
    public void isPasswordValid5False() {
        String pass = "T2#$%";
        assertFalse(PasswordValidator.isValid(pass));
    }

    @Test
    public void isPasswordValid6False() {
        String pass = "t2#$%";
        assertFalse(PasswordValidator.isValid(pass));
    }


}