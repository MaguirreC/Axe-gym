package com.example.pruebas.axegym.membership;

import com.example.pruebas.axegym.client.Client;
import com.example.pruebas.axegym.client.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        membership.setMembershipPlan(membershipPlan);
        membership.setActive(true);

        LocalDate startDate = requestMembership.starDate() != null ? requestMembership.starDate() : LocalDate.now();
        membership.setStarDate(startDate);

        LocalDate expirationDate = membership.calculateExpirationDate();
        membership.setExpirationDate(expirationDate);

        membership.setClient(client);
        System.out.println("este es el id del cliente" + client.getId());

        Membership savedMembership = membershipRepository.save(membership);
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

    public Page<ResponseMembership> getMemberships(int page,int size){
        Pageable pageable = PageRequest.of(page, size);

        return membershipRepository.findAll(pageable).map(membership -> new ResponseMembership(membership.getId(),
                membership.getClient().getName(),membership.getClient().getIdentification(),
                membership.getMembershipPlan().getName(),membership.getMembershipPlan().getPrice(), membership.getMembershipPlan().getDuration()
                ,membership.getStarDate(),membership.getExpirationDate(),membership.getActive()
                ));

    }

    public void deleteByid(Long id) {
        Membership membership = membershipRepository.findById(id).orElseThrow(() -> new RuntimeException("membership not found"));
        Client client = membership.getClient();
        if (client != null){
            client.removeMembership(membership);
            clientRepository.save(client);
        }
        membershipRepository.delete(membership);
    }

    public Membership updateActive(Long id){
        Membership membership = membershipRepository.findById(id).orElseThrow(()->new RuntimeException("membership not found"));
        membership.setActive(false);
        return membershipRepository.save(membership);

    }
    public Page<ResponseMembership> getMembershipsByStartDate(LocalDate startDate,int page,int size){
        Pageable pageable = PageRequest.of(page,size);

        return membershipRepository.findByStarDate(startDate,pageable).map(membership-> new ResponseMembership(
                membership.getId(),
                membership.getClient().getName(),membership.getClient().getIdentification(),
                membership.getMembershipPlan().getName(),membership.getMembershipPlan().getPrice(), membership.getMembershipPlan().getDuration()
                ,membership.getStarDate(),membership.getExpirationDate(),membership.getActive()
        ));
    }
}
