package com.example.pruebas.axegym.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findByLocalDate(LocalDate localDate);

    List<Schedule> findByTrainerId(Long id);
}
