package com.example.pruebas.axegym.membership;

import com.example.pruebas.axegym.client.ResponseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Page<ResponseMembership>> allClients(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(membershipService.getMemberships(page, size));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMembership(@PathVariable Long id){
        try {
            membershipService.deleteByid(id);
            return ResponseEntity.ok("membership deleted");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Membership> deactivateMembership(@PathVariable Long id){
        Membership updatedMembership = membershipService.updateActive(id);
        
        return ResponseEntity.ok(updatedMembership);
    }

    @GetMapping("/by-start-date")
    public Page<ResponseMembership> getMembershipsByDate(@RequestParam("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                         LocalDate startDate, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int size){
            return membershipService.getMembershipsByStartDate(startDate,page,size);


    }

}
