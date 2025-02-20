package com.example.pruebas.axegym.membership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;

public interface MembershipRepository extends JpaRepository<Membership,Long> {

     Page<Membership> findByStarDate(LocalDate startDate, Pageable pageable);

}
