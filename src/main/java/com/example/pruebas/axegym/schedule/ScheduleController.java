package com.example.pruebas.axegym.schedule;

import com.example.pruebas.axegym.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Schedule>> allSchedule(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size){
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
}
