package com.example.pruebas.axegym.schedule;

import com.example.pruebas.axegym.trainer.Trainer;
import com.example.pruebas.axegym.trainer.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule registerSchedule(DtoNewSchedule newSchedule){

        Trainer trainer = trainerRepository.findById(newSchedule.trainerId()).orElseThrow(()-> new RuntimeException("trainer not found"));

        Schedule schedule = new Schedule();

        schedule.setTrainer(trainer);
        schedule.setLocalDate(newSchedule.localDate());
        schedule.setShift(newSchedule.shift());

        return scheduleRepository.save(schedule);

    }
    public Page<DtoResponseSchedule> getSchedules(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return scheduleRepository.findAll(pageable).map(schedule-> new DtoResponseSchedule(
                schedule.getLocalDate(),schedule.getShift(),schedule.getTrainer().getName()
        ));
    }

    public void deleteScheduleById(Long id){
        if (!scheduleRepository.existsById(id)){
            throw new RuntimeException("schedule with ID "+ id + "not found");
        }
        scheduleRepository.deleteById(id);
    }

    public List<DtoResponseSchedule> getSchedulesByDate(LocalDate localDate){

        List<Schedule> schedules = scheduleRepository.findByLocalDate(localDate);
        return schedules.stream().map(schedule -> new DtoResponseSchedule(schedule.getLocalDate()
        ,schedule.getShift(),schedule.getTrainer().getName())).collect(Collectors.toList());
    }

    public List<DtoResponseSchedule> getSchedulesByTrainer(Long id){
        List<Schedule> schedules = scheduleRepository.findByTrainerId(id);
        return schedules.stream().map(schedule -> new DtoResponseSchedule(
                schedule.getLocalDate(),schedule.getShift(),schedule.getTrainer().getName()
        )).collect(Collectors.toList());
    }
}
