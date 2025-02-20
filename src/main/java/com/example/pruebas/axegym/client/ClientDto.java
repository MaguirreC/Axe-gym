package com.example.pruebas.axegym.client;

import java.time.LocalDate;

public record ClientDto(
        String name,
        String identification,
        String phoneNumber,
        LocalDate dateOfBirth
) {
}
