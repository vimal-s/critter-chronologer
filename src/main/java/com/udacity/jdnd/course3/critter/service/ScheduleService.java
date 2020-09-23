package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.data.schedule.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ScheduleRepository scheduleRepository;
  private final PetService petService;
  private final EmployeeService employeeService;

  public ScheduleService(
          ScheduleRepository scheduleRepository,
          PetService petService,
          EmployeeService employeeService) {
    this.scheduleRepository = scheduleRepository;
    this.petService = petService;
    this.employeeService = employeeService;
  }

  public Schedule create(Schedule schedule) {
    for (Long petId : schedule.getPetIds()) {
      if (!petService.exists(petId)) {
        throw new RuntimeException("Pet not found with id: " + petId);
      }
    }

    for (Long employeeId : schedule.getEmployeeIds()) {
      if (!employeeService.exists(employeeId)) {
        throw new RuntimeException("Employee not found with id: " + employeeId);
      }
    }

    return scheduleRepository.save(schedule);
  }

  public List<Schedule> getAllSchedules() {
    return scheduleRepository.findAll();
  }

  public List<Schedule> getAllByEmployee(Long id) {
    return scheduleRepository.findByEmployeeIds(id);
  }

  public List<Schedule> getAllByPet(Long id) {
    return scheduleRepository.findByPetIds(id);
  }

  public List<Schedule> getAllByOwner(Long id) {
    List<Schedule> schedules = new ArrayList<>();

    List<Pet> pets = petService.getPetsByOwner(id);
    for (Pet pet : pets) {
      schedules.addAll(getAllByPet(pet.getId()));
    }

    return schedules;
    /*
        return getAllSchedules().stream()
            .filter(
                schedule ->
                    schedule.getPetIds().stream()
                        .anyMatch(
                            petId -> {
                              try {
                                return petService.getOwnerByPet(petId).getId().equals(id);
                              } catch (Throwable throwable) {
                                throwable.printStackTrace();
                                return false;
                              }
                            }))
            .collect(Collectors.toList());
    */
  }
}
