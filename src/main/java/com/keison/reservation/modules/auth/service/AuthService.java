package com.keison.reservation.modules.auth.service;

import com.keison.reservation.common.enums.UserRole;
import com.keison.reservation.modules.auth.dto.*;
import com.keison.reservation.modules.passengers.entity.Passenger;
import com.keison.reservation.modules.passengers.repo.PassengerRepository;
import com.keison.reservation.modules.users.entity.User;
import com.keison.reservation.modules.users.repo.UserRepository;
import com.keison.reservation.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.locations.entity.*;
import com.keison.reservation.modules.locations.repo.VillageRepository;

import com.keison.reservation.modules.locations.repo.ProvinceRepository;
import com.keison.reservation.modules.locations.repo.DistrictRepository;
import com.keison.reservation.modules.locations.repo.SectorRepository;
import com.keison.reservation.modules.locations.repo.CellRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PassengerRepository passengerRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final VillageRepository villageRepo;

    private final ProvinceRepository provinceRepo;
    private final DistrictRepository districtRepo;
    private final SectorRepository sectorRepo;
    private final CellRepository cellRepo;


    public AuthService(
            UserRepository userRepo,
            PassengerRepository passengerRepo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            VillageRepository villageRepo,
            ProvinceRepository provinceRepo,
            DistrictRepository districtRepo,
            SectorRepository sectorRepo,
            CellRepository cellRepo
    ) {
        this.userRepo = userRepo;
        this.passengerRepo = passengerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.provinceRepo = provinceRepo;
        this.districtRepo = districtRepo;
        this.sectorRepo = sectorRepo;
        this.cellRepo = cellRepo;
        this.villageRepo = villageRepo;
    }


    public AuthResponse registerPassenger(RegisterPassengerRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        if (passengerRepo.existsByPassportNo(req.getPassportNo())) {
            throw new BadRequestException("Passport number already in use");
        }

        Province province = provinceRepo.findById(req.getProvinceId())
                .orElseThrow(() -> new BadRequestException("Province not found"));

        District district = districtRepo.findById(req.getDistrictId())
                .orElseThrow(() -> new BadRequestException("District not found"));

        if (!district.getProvince().getId().equals(province.getId())) {
            throw new BadRequestException("District does not belong to selected Province");
        }

        Sector sector = sectorRepo.findById(req.getSectorId())
                .orElseThrow(() -> new BadRequestException("Sector not found"));

        if (!sector.getDistrict().getId().equals(district.getId())) {
            throw new BadRequestException("Sector does not belong to selected District");
        }

        CellEntity cell = cellRepo.findById(req.getCellId())
                .orElseThrow(() -> new BadRequestException("Cell not found"));

        if (!cell.getSector().getId().equals(sector.getId())) {
            throw new BadRequestException("Cell does not belong to selected Sector");
        }

        Village village = villageRepo.findById(req.getVillageId())
                .orElseThrow(() -> new BadRequestException("Village not found"));

        if (!village.getCell().getId().equals(cell.getId())) {
            throw new BadRequestException("Village does not belong to selected Cell");
        }

        User user = new User();
        user.setEmail(req.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setPhoneNumber(req.getPhoneNumber());
        user.setVillage(village);
        user.setRole(UserRole.PASSENGER);
        user.setCreatedAt(Instant.now());

        user = userRepo.save(user);

        Passenger passenger = new Passenger();
        passenger.setUser(user);
        passenger.setFullName(req.getFullName());
        passenger.setPassportNo(req.getPassportNo());
        passenger.setNationality(req.getNationality());
        passenger.setDateOfBirth(req.getDateOfBirth());
        passenger.setGender(req.getGender());
        passenger.setCreatedAt(Instant.now());

        Passenger savedPassenger = passengerRepo.save(passenger);

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                savedPassenger.getId(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }


    public AuthResponse login(LoginRequest req) {

        User user = userRepo.findByEmail(req.getEmail().toLowerCase())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        UUID passengerId = null;
        if (user.getRole() == UserRole.PASSENGER) {
            Passenger p = passengerRepo.findByUserId(user.getId())
                    .orElse(null);
            if (p != null) passengerId = p.getId();
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                user.getId(),
                passengerId,
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }
}
