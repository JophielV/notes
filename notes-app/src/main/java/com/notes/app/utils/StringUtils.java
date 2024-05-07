package com.notes.app.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
