package com.example.pruebas.axegym.schedule;

import java.time.LocalDate;

public record DtoResponseSchedule(
        LocalDate date,
        Shift shift,
        String trainerName
) {
}
