package com.keison.reservation.security;

import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.users.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            throw new BadRequestException("Unauthorized");
        }
        return (User) auth.getPrincipal();
    }
}
