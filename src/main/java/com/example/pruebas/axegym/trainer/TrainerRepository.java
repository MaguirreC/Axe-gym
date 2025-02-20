package com.example.pruebas.axegym.trainer;

import com.example.pruebas.axegym.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {

    Optional<Trainer> findByNameContainingIgnoreCase(String name);

    public Trainer findByIdentification(String identification);
}
