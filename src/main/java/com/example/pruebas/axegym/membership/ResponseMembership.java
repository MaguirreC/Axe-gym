package com.example.pruebas.axegym.membership;

import java.time.LocalDate;

public record ResponseMembership(
        Long id,
        String clientName,
        String identification,
        String membershipPlanName,
        Double price ,
        Duration duration,
        LocalDate starDate,
        LocalDate expirationDate,
        Boolean active
) {
}
