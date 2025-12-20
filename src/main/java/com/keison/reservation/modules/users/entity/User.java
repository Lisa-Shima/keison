package com.keison.reservation.modules.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keison.reservation.common.enums.UserRole;
import com.keison.reservation.modules.locations.entity.Village;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 180)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 180)
    private String fullName;

    private String phoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "village_id")
    private com.keison.reservation.modules.locations.entity.Village village;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private com.keison.reservation.common.enums.UserRole role;

    @Column(nullable = false)
    private Instant createdAt;

    // ===== Getters & Setters =====
    public UUID getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Village getVillage() { return village; }
    public void setVillage(Village village) { this.village = village; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
