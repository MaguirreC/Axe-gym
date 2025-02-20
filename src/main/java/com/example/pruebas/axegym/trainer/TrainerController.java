package com.example.pruebas.axegym.trainer;

import com.example.pruebas.axegym.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/search-name")
    public ResponseEntity<Optional<Trainer>> getByName(@RequestParam String name){
       return ResponseEntity.ok(trainerService.findByTrainerName(name));
    }
    @GetMapping("/search")
    public ResponseEntity<Trainer> searchTrainer(@RequestParam String identification){
        Trainer trainer = trainerService.getTrainerByIdentification(identification);
        return ResponseEntity.ok(trainer);
    }
}
