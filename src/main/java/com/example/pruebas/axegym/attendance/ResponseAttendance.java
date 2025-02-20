package com.example.pruebas.axegym.attendance;

import java.time.LocalDateTime;

public record ResponseAttendance(
        String clientName,
        Boolean membershipActive,
        String membershipPlanName,
        LocalDateTime timeAttendance
) {
}
