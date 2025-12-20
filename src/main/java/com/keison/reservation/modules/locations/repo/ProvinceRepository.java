package com.keison.reservation.modules.locations.repo;

import com.keison.reservation.modules.locations.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProvinceRepository extends JpaRepository<Province, UUID> {}
