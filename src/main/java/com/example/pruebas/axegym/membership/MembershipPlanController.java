package com.example.pruebas.axegym.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class MembershipPlanController {

    @Autowired
    MembershipPlanService membershipPlanService;

    @PostMapping
    public ResponseEntity<MembershipPlan> createPlan(@RequestBody MembershipPlan plan){
        MembershipPlan savedPlan = membershipPlanService.createPlan(plan);
        return ResponseEntity.ok(savedPlan);
    }

    @GetMapping
    public ResponseEntity<List<MembershipPlan>> getPlans(){
        return ResponseEntity.ok(membershipPlanService.getAllPlans());
    }
}
