package com.eduardo.apiservidor.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {
    private EmailUtil() {
        throw new UnsupportedOperationException("Esta classe utilitária não pode ser instanciada.");
    }

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
