package com.keison.reservation.modules.locations.controller;

import com.keison.reservation.modules.locations.dto.*;
import com.keison.reservation.modules.locations.entity.*;
import com.keison.reservation.modules.locations.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    // ===== GET for cascading dropdowns =====

    @GetMapping("/provinces")
    public List<LocationItem> provinces() {
        return service.provinces();
    }

    @GetMapping("/districts")
    public List<LocationItem> districts(@RequestParam UUID provinceId) {
        return service.districts(provinceId);
    }

    @GetMapping("/sectors")
    public List<LocationItem> sectors(@RequestParam UUID districtId) {
        return service.sectors(districtId);
    }

    @GetMapping("/cells")
    public List<LocationItem> cells(@RequestParam UUID sectorId) {
        return service.cells(sectorId);
    }

    @GetMapping("/villages")
    public List<LocationItem> villages(@RequestParam UUID cellId) {
        return service.villages(cellId);
    }

    // ===== POST for manual seeding =====

    @PostMapping("/provinces")
    public Province createProvince(@Valid @RequestBody CreateProvinceRequest req) {
        return service.createProvince(req);
    }

    @PostMapping("/districts")
    public District createDistrict(@Valid @RequestBody CreateDistrictRequest req) {
        return service.createDistrict(req);
    }

    @PostMapping("/sectors")
    public Sector createSector(@Valid @RequestBody CreateSectorRequest req) {
        return service.createSector(req);
    }

    @PostMapping("/cells")
    public CellEntity createCell(@Valid @RequestBody CreateCellRequest req) {
        return service.createCell(req);
    }

    @PostMapping("/villages")
    public Village createVillage(@Valid @RequestBody CreateVillageRequest req) {
        return service.createVillage(req);
    }
}
