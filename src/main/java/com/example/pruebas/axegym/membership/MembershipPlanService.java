package com.example.pruebas.axegym.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipPlanService {

    @Autowired
    MembershipPlanRepository membershipPlanRepository;


    public MembershipPlan createPlan(MembershipPlan plan){
        return membershipPlanRepository.save(plan);
    }

    public List<MembershipPlan> getAllPlans(){
        return membershipPlanRepository.findAll();
    }
}
