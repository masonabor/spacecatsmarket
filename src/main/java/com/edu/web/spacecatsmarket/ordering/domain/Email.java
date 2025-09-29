package com.edu.web.spacecatsmarket.ordering.domain;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(String email) {

    public Email {
        Assert.notNull(email, "email must not be null");
        Assert.isTrue(isValidEmail(email), "invalid email");
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher =  pattern.matcher(email);
        return matcher.matches();
    }
}
