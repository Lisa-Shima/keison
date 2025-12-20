package com.keison.reservation.modules.admin.controller;

import com.keison.reservation.modules.admin.dto.CreateStaffRequest;
import com.keison.reservation.modules.admin.service.AdminUserService;
import com.keison.reservation.modules.users.entity.User;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping("/staff")
    public User createStaff(@Valid @RequestBody CreateStaffRequest req) {
        return adminUserService.createStaff(req);
    }
}
