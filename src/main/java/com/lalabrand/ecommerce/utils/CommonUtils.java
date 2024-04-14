package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommonUtils {
    public static boolean isIdInvalid(String id) {
        return id == null || id.isEmpty() || id.length() > 36;
    }

    public static boolean isIdsInvalid(Collection<String> ids) {
        return ids.stream().allMatch(id -> id == null || id.isEmpty() || id.length() > 36);
    }

    public UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
