package com.example.pruebas.axegym.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membership")
public class MembershipController {
    @Autowired
    MembershipService membershipService;

    @PostMapping
    public ResponseEntity<Membership> newMembership(@RequestBody RequestMembership requestMembership){
        Membership membership = membershipService.createMembership(requestMembership);
        return ResponseEntity.ok(membership);

    }

    @GetMapping
    public ResponseEntity<List<Membership>> listMemberships(){
        membershipService.checkMembershipsStatus();
        return ResponseEntity.ok(membershipService.getMemberships());
    }

}
