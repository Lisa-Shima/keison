package com.keison.reservation.modules.locations.service;

import com.keison.reservation.common.exception.BadRequestException;
import com.keison.reservation.modules.locations.dto.*;
import com.keison.reservation.modules.locations.entity.*;
import com.keison.reservation.modules.locations.repo.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

    private final ProvinceRepository provinceRepo;
    private final DistrictRepository districtRepo;
    private final SectorRepository sectorRepo;
    private final CellRepository cellRepo;
    private final VillageRepository villageRepo;

    public LocationService(
            ProvinceRepository provinceRepo,
            DistrictRepository districtRepo,
            SectorRepository sectorRepo,
            CellRepository cellRepo,
            VillageRepository villageRepo
    ) {
        this.provinceRepo = provinceRepo;
        this.districtRepo = districtRepo;
        this.sectorRepo = sectorRepo;
        this.cellRepo = cellRepo;
        this.villageRepo = villageRepo;
    }

    // ===== GET (for cascading dropdowns) =====

    public List<LocationItem> provinces() {
        return provinceRepo.findAll().stream()
                .map(p -> new LocationItem(p.getId(), p.getName()))
                .toList();
    }

    public List<LocationItem> districts(UUID provinceId) {
        provinceRepo.findById(provinceId).orElseThrow(() -> new BadRequestException("Province not found"));
        return districtRepo.findByProvinceId(provinceId).stream()
                .map(d -> new LocationItem(d.getId(), d.getName()))
                .toList();
    }

    public List<LocationItem> sectors(UUID districtId) {
        districtRepo.findById(districtId).orElseThrow(() -> new BadRequestException("District not found"));
        return sectorRepo.findByDistrictId(districtId).stream()
                .map(s -> new LocationItem(s.getId(), s.getName()))
                .toList();
    }

    public List<LocationItem> cells(UUID sectorId) {
        sectorRepo.findById(sectorId).orElseThrow(() -> new BadRequestException("Sector not found"));
        return cellRepo.findBySectorId(sectorId).stream()
                .map(c -> new LocationItem(c.getId(), c.getName()))
                .toList();
    }

    public List<LocationItem> villages(UUID cellId) {
        cellRepo.findById(cellId).orElseThrow(() -> new BadRequestException("Cell not found"));
        return villageRepo.findByCellId(cellId).stream()
                .map(v -> new LocationItem(v.getId(), v.getName()))
                .toList();
    }

    // ===== POST (to seed data manually via API) =====

    public Province createProvince(CreateProvinceRequest req) {
        Province p = new Province();
        p.setName(req.getName());
        p.setCode(req.getCode());
        return provinceRepo.save(p);
    }

    public District createDistrict(CreateDistrictRequest req) {
        Province province = provinceRepo.findById(req.getProvinceId())
                .orElseThrow(() -> new BadRequestException("Province not found"));

        District d = new District();
        d.setName(req.getName());
        d.setProvince(province);
        return districtRepo.save(d);
    }

    public Sector createSector(CreateSectorRequest req) {
        District district = districtRepo.findById(req.getDistrictId())
                .orElseThrow(() -> new BadRequestException("District not found"));

        Sector s = new Sector();
        s.setName(req.getName());
        s.setDistrict(district);
        return sectorRepo.save(s);
    }

    public CellEntity createCell(CreateCellRequest req) {
        Sector sector = sectorRepo.findById(req.getSectorId())
                .orElseThrow(() -> new BadRequestException("Sector not found"));

        CellEntity c = new CellEntity();
        c.setName(req.getName());
        c.setSector(sector);
        return cellRepo.save(c);
    }

    public Village createVillage(CreateVillageRequest req) {
        CellEntity cell = cellRepo.findById(req.getCellId())
                .orElseThrow(() -> new BadRequestException("Cell not found"));

        Village v = new Village();
        v.setName(req.getName());
        v.setCell(cell);
        return villageRepo.save(v);
    }
}
