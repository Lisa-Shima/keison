//package com.keison.reservation.config;
//
//import com.keison.reservation.common.enums.UserRole;
//import com.keison.reservation.modules.users.entity.User;
//import jakarta.persistence.PrePersist;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//
//@Component
//public class EntityDefaults {
//
//    @PrePersist
//    public void prePersist(Object entity) {
//        if (entity instanceof User user) {
//            if (user.getCreatedAt() == null) user.setCreatedAt(Instant.now());
//            if (user.getRole() == null) user.setRole(UserRole.PASSENGER);
//        }
//    }
//}
