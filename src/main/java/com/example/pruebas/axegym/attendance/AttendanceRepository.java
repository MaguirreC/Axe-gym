package com.example.pruebas.axegym.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

    public Page<Attendance> findAttendanceByClientId(Long clientId, Pageable pageable);
}
