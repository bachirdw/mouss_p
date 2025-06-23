package com.gestion.location.service;

import com.gestion.location.model.Vehicule;
import com.gestion.location.repository.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    public Vehicule addVehicule(Vehicule v) {
        return vehiculeRepository.save(v);
    }

    // Tu peux aussi ajouter : findById, deleteById, etc.
}
