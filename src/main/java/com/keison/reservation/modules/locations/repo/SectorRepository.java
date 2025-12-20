package com.keison.reservation.modules.locations.repo;

import com.keison.reservation.modules.locations.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
    List<Sector> findByDistrictId(UUID districtId);
}
