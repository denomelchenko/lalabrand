package com.lalabrand.ecommerce.utils;

import com.lalabrand.ecommerce.exception.AccessDeniedException;
import com.lalabrand.ecommerce.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommonUtils {
    public static boolean isIdsInvalid(Collection<String> ids) {
        return ids.stream().anyMatch(id -> id == null || id.isEmpty() || id.contains(" ") || id.length() > 36);
    }

    public static String getCurrentUserId() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
            throw new AccessDeniedException("User is not authenticated");
        }
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
