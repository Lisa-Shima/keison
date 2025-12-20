package com.keison.reservation.modules.employees.repo;

import com.keison.reservation.modules.employees.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
}
