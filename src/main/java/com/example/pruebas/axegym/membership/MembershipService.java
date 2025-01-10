package com.example.pruebas.axegym.membership;

import com.example.pruebas.axegym.client.Client;
import com.example.pruebas.axegym.client.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService {
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    MembershipPlanRepository membershipPlanRepository;

    public Membership createMembership(RequestMembership requestMembership){
        Client client = clientRepository.findById(requestMembership.clientId()).orElseThrow(()->new RuntimeException("Client not found"));

        MembershipPlan membershipPlan = membershipPlanRepository.findById(requestMembership.membershipPlanId()).orElseThrow(()->new RuntimeException("plan not found"));

        Membership membership = new Membership();
        membership.setClient(client);
        membership.setMembershipPlan(membershipPlan);
        membership.setActive(true);

        LocalDate startDate = requestMembership.starDate() != null ? requestMembership.starDate() : LocalDate.now();
        membership.setStarDate(startDate);

        LocalDate expirationDate = membership.calculateExpirationDate();
        membership.setExpirationDate(expirationDate);

        Membership savedMembership = membershipRepository.save(membership);
        client.setMembership(savedMembership);
        clientRepository.save(client);
        return savedMembership;
    }

    @Transactional
    public void checkMembershipsStatus(){
        List<Membership> memberships = membershipRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Membership membership : memberships){
            if (membership.getExpirationDate() != null && membership.getExpirationDate().isBefore(today) && membership.isActive()){
                membership.setActive(false);
                membershipRepository.save(membership);
            }
        }
    }
//nota tenemos que traer el cliente cuando traemos las membresias

    public List<Membership> getMemberships(){
        List<Membership> memberships = membershipRepository.findAll();
        memberships.stream().
    }
}
