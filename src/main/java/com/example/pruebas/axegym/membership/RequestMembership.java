package com.example.pruebas.axegym.membership;

import java.time.LocalDate;

public record RequestMembership(
        Long clientId,
        Long membershipPlanId,
        LocalDate starDate,
        LocalDate expirationDate,
        Boolean active
) {
}
