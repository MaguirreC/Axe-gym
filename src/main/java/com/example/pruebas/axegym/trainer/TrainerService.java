package com.example.pruebas.axegym.trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
