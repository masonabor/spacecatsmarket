package com.edu.web.spacecatsmarket.ordering.domain;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record PhoneNumber(String phoneNumber) {

    public PhoneNumber {
        Assert.notNull(phoneNumber, "phone number must not be null");
        Assert.isTrue(isValidPhoneNumber(phoneNumber), "invalid phone number");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneNumberPattern = "^\\+?[0-9\\s\\-()]{7,15}$\n";
        Pattern pattern = Pattern.compile(phoneNumberPattern);
        Matcher matcher =  pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
