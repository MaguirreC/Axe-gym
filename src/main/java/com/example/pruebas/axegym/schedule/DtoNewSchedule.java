package com.example.pruebas.axegym.schedule;

import java.time.LocalDate;

public record DtoNewSchedule(
Long trainerId,
LocalDate localDate,
Shift shift
) {
}
