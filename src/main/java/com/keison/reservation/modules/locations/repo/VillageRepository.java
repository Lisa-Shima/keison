package com.keison.reservation.modules.locations.repo;

import com.keison.reservation.modules.locations.entity.Village;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VillageRepository extends JpaRepository<Village, UUID> {
    List<Village> findByCellId(UUID cellId);
}
