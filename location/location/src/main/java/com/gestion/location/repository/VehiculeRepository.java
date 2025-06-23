package com.gestion.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.location.model.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
