package com.rkrasucki.rbox.utilities.validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rkrasucki 24.05.19
 */

class EmailValidator {


    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    static boolean isValid(final String stringToMatch) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (stringToMatch == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }
}
