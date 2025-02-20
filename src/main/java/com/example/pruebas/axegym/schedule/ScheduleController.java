package com.example.pruebas.axegym.schedule;

import com.example.pruebas.axegym.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> newSchedule(@RequestBody DtoNewSchedule dtoNewSchedule){
        Schedule schedule = scheduleService.registerSchedule(dtoNewSchedule);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping
    public ResponseEntity<Page<DtoResponseSchedule>> allSchedule(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(scheduleService.getSchedules(page, size));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        try {
            scheduleService.deleteScheduleById(id);
            return ResponseEntity.ok("schedule delete");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/date")
    public ResponseEntity<List<DtoResponseSchedule>> getSchedulesByDate(@RequestParam LocalDate date){
        List<DtoResponseSchedule> schedules = scheduleService.getSchedulesByDate(date);
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/id")
    public ResponseEntity<List<DtoResponseSchedule>> getSchedulesByTrainer(@RequestParam Long id){
        List<DtoResponseSchedule> schedules = scheduleService.getSchedulesByTrainer(id);
        return ResponseEntity.ok(schedules);
    }
}
