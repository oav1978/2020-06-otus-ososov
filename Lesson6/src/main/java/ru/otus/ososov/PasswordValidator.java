package ru.otus.ososov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private Pattern pattern;
    private Matcher matcher;
    private static String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!№%?()@#$%^&*]).{6,20})";
    private static String PASSWORD_PATTERN_ADMIN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!№%?()@#$%^&*]).{8,20})";

    public PasswordValidator(boolean forAdmin) {
        if (forAdmin)
            pattern = Pattern.compile(PASSWORD_PATTERN_ADMIN);
        else
            pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean validate(final String password) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
