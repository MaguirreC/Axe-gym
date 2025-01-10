package com.example.pruebas.axegym.client;

import java.time.LocalDate;

public record ResponseClient(
        String nameClient,
        String identificationClient,
        Boolean active,
        String membershipPlan,
        String phoneNumber,
        LocalDate dateOfBirth
) {
}
