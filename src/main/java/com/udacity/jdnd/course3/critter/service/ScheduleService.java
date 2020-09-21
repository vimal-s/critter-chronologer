package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ScheduleRepository scheduleRepository;
  private final PetService petService;
  private final UserService userService;

  public ScheduleService(ScheduleRepository scheduleRepository, PetService petService, UserService userService) {
    this.scheduleRepository = scheduleRepository;
    this.petService = petService;
    this.userService = userService;
  }

  // todo: how can I combine the two checks
  public Schedule create(Schedule schedule) {
    logger.info("Saving to db: " + schedule);

    schedule.getPetIds().forEach(petId -> {
      if (!petService.exists(petId)) {
        throw new RuntimeException("Pet not found with id: " + petId);
      }
    });

    schedule.getEmployeeIds().forEach(employeeId -> {
      if (!userService.exists(employeeId)) {
        throw new RuntimeException("Employee not found with id: " + employeeId);
      }
    });

    return scheduleRepository.save(schedule);
  }

  public List<Schedule> getAllSchedules() {
    logger.info("Retrieving all from db");
    return scheduleRepository.findAll();
  }

  public List<Schedule> getAllByEmployee(Long id) {
    logger.info("Finding by employee with id: " + id);
    return scheduleRepository.findByEmployeeIds(id);
  }

  public List<Schedule> getAllByPet(Long id) {
    logger.info("Finding by pet with id: " + id);
    return scheduleRepository.findByPetIds(id);
  }

  public List<Schedule> getAllByOwner(Long id) {
    logger.info("Finding by owner with id: " + id);
    return getAllSchedules().stream()
        .filter(
            schedule ->
                schedule.getPetIds().stream()
                    .anyMatch(
                        aLong -> {
                          try {
                            return petService.getOwnerByPet(aLong).getId().equals(id);
                          } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            throw new RuntimeException("wrong implementation");
                          }
                        }))
        .collect(Collectors.toList());
  }
}
