package com.example.pruebas.axegym.client;

import com.example.pruebas.axegym.membership.Membership;
import com.example.pruebas.axegym.membership.MembershipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MembershipRepository membershipRepository;
    public Client registerClient(ClientDto clientDto){
        Client client = new Client();
        client.setName(clientDto.name());
        client.setIdentification(clientDto.identification());
        client.setPhoneNumber(clientDto.phoneNumber());
        client.setDateOfBirth(clientDto.dateOfBirth());
        return clientRepository.save(client);
    }


    public Page<ResponseClient> getClients(int page,int size){
        Pageable pageable = PageRequest.of(page, size);

        return clientRepository.findAll(pageable).map(client->{
            Membership activeMembership = client.getMemberships().stream()
                    .filter(Membership::getActive)
                    .findFirst()
                    .orElse(null);

            String membershipPlanName = (activeMembership != null && activeMembership.getMembershipPlan() != null)
                    ? activeMembership.getMembershipPlan().getName()
                    : "no membership plan";

            boolean membershipStatus = activeMembership != null;

            return new ResponseClient(
                    client.getName(),
                    client.getIdentification(),
                    membershipStatus,
                    membershipPlanName,
                    client.getPhoneNumber(),
                    client.getDateOfBirth()
            );
        });


    }

    @Transactional
    public void deleteClientByid(Long id){
        Client client = clientRepository.findById(id).orElseThrow(()->new RuntimeException("client not found"));
        List<Membership> memberships = client.getMemberships();

        for (Membership membership : memberships){
            membership.setActive(false);
            membership.setClient(null);
            membershipRepository.save(membership);
        }
        clientRepository.delete(client);

    }

    public Client updateClient(Long clientId, RequestUpdateClient request){
        Client client = clientRepository.findById(clientId).orElseThrow(()->new RuntimeException("client not found"));

        if (request.name() != null){
            client.setName(request.name());
        }
        if (request.identification() != null){
            client.setIdentification(request.identification());
        }
        if (request.phoneNumber()!= null){
            client.setPhoneNumber(request.phoneNumber());
        }
        if (request.dateOfBirth() != null){
            client.setDateOfBirth(request.dateOfBirth());
        }

        return clientRepository.save(client);

    }

    public Client getClientByIdentification(String identification){
        Client client = clientRepository.findByIdentification(identification);
        if (client == null){
            throw new RuntimeException("client with identification: " + identification + " doesn't exist");
        }
        return client;
    }
}
