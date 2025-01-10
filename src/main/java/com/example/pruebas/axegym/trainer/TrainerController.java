package com.example.pruebas.axegym.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
   @Autowired
    TrainerService trainerService;

   @PostMapping
    public ResponseEntity<Trainer> newTrainer(@RequestBody Trainer trainer){

       return ResponseEntity.ok(trainerService.registerTrainer(trainer));

    }

    @GetMapping
    public ResponseEntity<List<Trainer>> getTrainers(){
       return ResponseEntity.ok(trainerService.getTrainers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Long id){
        try {
            trainerService.deleteTrainerByid(id);
            return ResponseEntity.ok("trainer deleted");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
