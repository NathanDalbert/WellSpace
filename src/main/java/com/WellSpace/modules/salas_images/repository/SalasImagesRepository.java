package com.WellSpace.modules.salas_images.repository;

import com.WellSpace.modules.salas_images.domain.SalasImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalasImagesRepository extends JpaRepository<SalasImages, UUID> {
}
