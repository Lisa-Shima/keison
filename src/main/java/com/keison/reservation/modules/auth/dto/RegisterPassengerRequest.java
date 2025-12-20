package com.keison.reservation.modules.auth.dto;

import com.keison.reservation.common.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public class RegisterPassengerRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    private String fullName;

    private String phoneNumber;

    @NotBlank
    private String passportNo;

    @NotBlank
    private String nationality;

    @NotNull
    @Past(message = "dateOfBirth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    // ✅ Location hierarchy IDs
    @NotNull
    private UUID provinceId;

    @NotNull
    private UUID districtId;

    @NotNull
    private UUID sectorId;

    @NotNull
    private UUID cellId;

    @NotNull
    private UUID villageId;

    // ===== Getters & Setters =====

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassportNo() { return passportNo; }
    public void setPassportNo(String passportNo) { this.passportNo = passportNo; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public UUID getProvinceId() { return provinceId; }
    public void setProvinceId(UUID provinceId) { this.provinceId = provinceId; }

    public UUID getDistrictId() { return districtId; }
    public void setDistrictId(UUID districtId) { this.districtId = districtId; }

    public UUID getSectorId() { return sectorId; }
    public void setSectorId(UUID sectorId) { this.sectorId = sectorId; }

    public UUID getCellId() { return cellId; }
    public void setCellId(UUID cellId) { this.cellId = cellId; }

    public UUID getVillageId() { return villageId; }
    public void setVillageId(UUID villageId) { this.villageId = villageId; }
}
