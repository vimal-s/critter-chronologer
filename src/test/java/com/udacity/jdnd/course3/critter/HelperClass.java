package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.presentation.pet.PetDTO;
import com.udacity.jdnd.course3.critter.presentation.pet.PetType;
import com.udacity.jdnd.course3.critter.presentation.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.presentation.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class HelperClass {

  static EmployeeDTO createEmployeeDTO() {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    employeeDTO.setName("TestEmployee");
    employeeDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
    return employeeDTO;
  }

  static CustomerDTO createCustomerDTO() {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("TestEmployee");
    customerDTO.setPhoneNumber("123-456-789");
    return customerDTO;
  }

  static PetDTO createPetDTO() {
    PetDTO petDTO = new PetDTO();
    petDTO.setName("TestPet");
    petDTO.setType(PetType.CAT);
    return petDTO;
  }

  // todo: delete this later
  private static EmployeeRequestDTO createEmployeeRequestDTO() {
    EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
    employeeRequestDTO.setDate(LocalDate.of(2019, 12, 25));
    employeeRequestDTO.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
    return employeeRequestDTO;
  }

  static ScheduleDTO createScheduleDTO(
      List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<EmployeeSkill> activities) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();
    scheduleDTO.setPetIds(petIds);
    scheduleDTO.setEmployeeIds(employeeIds);
    scheduleDTO.setDate(date);
    scheduleDTO.setActivities(activities);
    return scheduleDTO;
  }

  static void compareSchedules(ScheduleDTO sched1, ScheduleDTO sched2) {
    Assertions.assertArrayEquals(sched1.getPetIds().toArray(), sched2.getPetIds().toArray());
    // todo: debug if activity objects are same or different here
    Assertions.assertEquals(sched1.getActivities(), sched2.getActivities());
    Assertions.assertArrayEquals(
        sched1.getEmployeeIds().toArray(), sched2.getEmployeeIds().toArray());
    Assertions.assertEquals(sched1.getDate(), sched2.getDate());
  }
}
