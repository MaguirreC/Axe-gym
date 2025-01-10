package com.example.pruebas.axegym.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client registerClient(Client client){
            return clientRepository.save(client);
    }


    public Page<ResponseClient> getClients(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable).map(client -> {
            String membershipPlanName = (client.getMembership() != null && client.getMembership().getMembershipPlan() != null)
                    ? client.getMembership().getMembershipPlan().getName():"no membership plan";

            boolean membershipStatus = (client.getMembership() != null && client.getMembership().getActive() != null)
                    ? client.getMembership().getActive()
                    : false;
            return new ResponseClient(
                    client.getName(), client.getIdentification(), membershipStatus,
                    membershipPlanName,client.getPhoneNumber(),client.getDateOfBirth()
            );
        });



    }

    public void deleteClientByid(Long id){
        if (!clientRepository.existsById(id)){
            throw new RuntimeException("client with ID "+ id + "not found");
        }
            clientRepository.deleteById(id);
    }
}
