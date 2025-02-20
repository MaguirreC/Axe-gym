package com.example.pruebas.axegym.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    public List<Client> findClientById(Long id);

    public Client findByIdentification(String identification);
}
