package com.daybison.analytree_plus.utils;

public class ValidationUtils {

    public static String valueReturnNotNull(String value) {
        return (value != null && !value.isEmpty()) ? value : "0";
    }
}
