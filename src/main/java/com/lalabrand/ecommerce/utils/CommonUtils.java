package com.lalabrand.ecommerce.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public static boolean isIdValid(Integer id) {
        return id != null && id > 0;
    }
}
