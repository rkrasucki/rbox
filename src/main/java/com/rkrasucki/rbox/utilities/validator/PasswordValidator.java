package com.rkrasucki.rbox.utilities.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rkrasucki 26.05.19
 */

class PasswordValidator {

    // Minimum 4 characters, at least one uppercase letter, one lowercase letter, one number and one special character:
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_])[A-Za-z\\d@$!%*?&_]{4,}$";

    static boolean isValid(final String stringToMatch) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (stringToMatch == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }

}
