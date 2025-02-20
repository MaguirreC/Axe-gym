package com.example.pruebas.axegym.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;


    @PostMapping("/{clientId}")
    public ResponseEntity<ResponseAttendance> postAttendance(@PathVariable Long clientId){
        ResponseAttendance responseAttendance =attendanceService.registerAttendance(clientId);
        return ResponseEntity.ok(responseAttendance);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseAttendance>> getAttendances(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size) {
        return ResponseEntity.ok(attendanceService.getAttendances(page, size));
    }
    @GetMapping("/client/{clientId}     ")
    public ResponseEntity<Page<ResponseAttendance>> getAttendancesByClient(@PathVariable Long clientId,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(attendanceService.getAttendancesByClientId(clientId,page,size));
    }
}
