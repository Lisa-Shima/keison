package com.keison.reservation.modules.locations.repo;

import com.keison.reservation.modules.locations.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DistrictRepository extends JpaRepository<District, UUID> {
    List<District> findByProvinceId(UUID provinceId);
}
