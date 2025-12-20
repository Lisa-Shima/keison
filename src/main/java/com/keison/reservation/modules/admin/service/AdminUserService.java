package com.keison.reservation.modules.admin.service;

import com.keison.reservation.common.enums.UserRole;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.admin.dto.CreateStaffRequest;
import com.keison.reservation.modules.employees.entity.Employee;
import com.keison.reservation.modules.employees.repo.EmployeeRepository;
import com.keison.reservation.modules.users.entity.User;
import com.keison.reservation.modules.users.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AdminUserService {

    private final UserRepository userRepo;
    private final EmployeeRepository employeeRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminUserService(UserRepository userRepo, EmployeeRepository employeeRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createStaff(CreateStaffRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (userRepo.existsByEmail(email)) {
            throw new BadRequestException("Email already in use");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setFullName(req.getFullName().trim());
        u.setPhoneNumber(req.getPhoneNumber());
        u.setRole(UserRole.STAFF);
        u.setCreatedAt(Instant.now());

        // ✅ no village required for staff/admin
        u = userRepo.save(u);

        // Optional employee profile (only if provided)
        boolean hasDept = req.getDepartment() != null && !req.getDepartment().trim().isEmpty();
        boolean hasPos = req.getPosition() != null && !req.getPosition().trim().isEmpty();

        if (hasDept || hasPos) {
            Employee e = new Employee();
            e.setUser(u);
            e.setDepartment(hasDept ? req.getDepartment().trim() : "General");
            e.setPosition(hasPos ? req.getPosition().trim() : "Staff");
            e.setCreatedAt(Instant.now());
            employeeRepo.save(e);
        }

        return u;
    }
}
