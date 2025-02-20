package com.example.pruebas.axegym.trainer;

import com.example.pruebas.axegym.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {
    @Autowired
    TrainerRepository trainerRepository;


    public Trainer registerTrainer(Trainer trainer){
        return trainerRepository.save(trainer);

    }

    public List<Trainer> getTrainers(){
        return trainerRepository.findAll();
    }


    public void deleteTrainerByid(Long id){
        if (!trainerRepository.existsById(id)){
            throw new RuntimeException("Trainer with ID "+ id + "not found");
        }
       trainerRepository.deleteById(id);
    }

    public Optional<Trainer> findByTrainerName(String name){
        return trainerRepository.findByNameContainingIgnoreCase(name);
    }

    public Trainer getTrainerByIdentification(String identification){
       Trainer trainer = trainerRepository.findByIdentification(identification);
        if (trainer == null){
            throw new RuntimeException("trainer with identification: " + identification + " doesn't exist");
        }
        return trainer;
    }
}
