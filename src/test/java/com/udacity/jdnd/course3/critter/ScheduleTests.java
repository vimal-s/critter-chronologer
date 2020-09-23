package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.presentation.pet.PetController;
import com.udacity.jdnd.course3.critter.presentation.pet.PetDTO;
import com.udacity.jdnd.course3.critter.presentation.schedule.ScheduleController;
import com.udacity.jdnd.course3.critter.presentation.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.presentation.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.udacity.jdnd.course3.critter.HelperClass.*;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class ScheduleTests {

  @Autowired private CustomerController customerController;

  @Autowired private PetController petController;

  @Autowired private EmployeeController employeeController;

  @Autowired private ScheduleController scheduleController;

  @Test
  public void testSchedulePetsForServiceWithEmployee() throws Throwable {
    EmployeeDTO employeeTemp = createEmployeeDTO();
    employeeTemp.setDaysAvailable(
        Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
    EmployeeDTO employeeDTO = employeeController.saveEmployee(employeeTemp);
    CustomerDTO customerDTO = customerController.saveCustomer(createCustomerDTO());
    PetDTO petTemp = createPetDTO();
    petTemp.setOwnerId(customerDTO.getId());
    PetDTO petDTO = petController.savePet(petTemp);

    LocalDate date = LocalDate.of(2019, 12, 25);
    List<Long> petList = Lists.newArrayList(petDTO.getId());
    List<Long> employeeList = Lists.newArrayList(employeeDTO.getId());
    Set<EmployeeSkill> skillSet = Sets.newHashSet(EmployeeSkill.PETTING);

    scheduleController.createSchedule(createScheduleDTO(petList, employeeList, date, skillSet));
    ScheduleDTO scheduleDTO = scheduleController.getAllSchedules().get(0);

    Assertions.assertEquals(scheduleDTO.getActivities(), skillSet);
    Assertions.assertEquals(scheduleDTO.getDate(), date);
    // todo: hibernate persistent bag vs java arraylist
    Assertions.assertArrayEquals(scheduleDTO.getEmployeeIds().toArray(), employeeList.toArray());
    Assertions.assertArrayEquals(scheduleDTO.getPetIds().toArray(), petList.toArray());
  }

  @Test
  public void testFindScheduleByEntities() throws Throwable {
    ScheduleDTO sched1 =
        populateSchedule(
            1,
            2,
            LocalDate.of(2019, 12, 25),
            Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.WALKING));
    ScheduleDTO sched2 =
        populateSchedule(3, 1, LocalDate.of(2019, 12, 26), Sets.newHashSet(EmployeeSkill.PETTING));

    // add a third schedule that shares some employees and pets with the other schedules
    ScheduleDTO sched3 = new ScheduleDTO();
    sched3.setEmployeeIds(sched1.getEmployeeIds());
    sched3.setPetIds(sched2.getPetIds());
    sched3.setActivities(Sets.newHashSet(EmployeeSkill.SHAVING, EmployeeSkill.PETTING));
    sched3.setDate(LocalDate.of(2020, 3, 23));
    scheduleController.createSchedule(sched3);

    /*
       We now have 3 schedule entries. The third schedule entry has the same employees as the 1st schedule
       and the same pets/owners as the second schedule. So if we look up schedule entries for the employee from
       schedule 1, we should get both the first and third schedule as our result.
    */

    // Employee 1 in is both schedule 1 and 3
    List<ScheduleDTO> scheds1e =
        scheduleController.getScheduleForEmployee(sched1.getEmployeeIds().get(0));
    compareSchedules(sched1, scheds1e.get(0));
    compareSchedules(sched3, scheds1e.get(1));

    // Employee 2 is only in schedule 2
    List<ScheduleDTO> scheds2e =
        scheduleController.getScheduleForEmployee(sched2.getEmployeeIds().get(0));
    compareSchedules(sched2, scheds2e.get(0));

    // Pet 1 is only in schedule 1
    List<ScheduleDTO> scheds1p = scheduleController.getScheduleForPet(sched1.getPetIds().get(0));
    compareSchedules(sched1, scheds1p.get(0));

    // Pet from schedule 2 is in both schedules 2 and 3
    // todo: why no error here?
    List<ScheduleDTO> scheds2p = scheduleController.getScheduleForPet(sched2.getPetIds().get(0));
    compareSchedules(sched2, scheds2p.get(0));
    compareSchedules(sched3, scheds2p.get(1));

    // Owner of the first pet will only be in schedule 1
    List<ScheduleDTO> scheds1c =
        scheduleController.getScheduleForCustomer(
            customerController.getOwnerByPet(sched1.getPetIds().get(0)).getId());
    compareSchedules(sched1, scheds1c.get(0));

    // Owner of pet from schedule 2 will be in both schedules 2 and 3
    List<ScheduleDTO> scheds2c =
        scheduleController.getScheduleForCustomer(
            customerController.getOwnerByPet(sched2.getPetIds().get(0)).getId());
    compareSchedules(sched2, scheds2c.get(0));
    compareSchedules(sched3, scheds2c.get(1));
  }

  private ScheduleDTO populateSchedule(
      int numEmployees, int numPets, LocalDate date, Set<EmployeeSkill> activities) {
    List<Long> employeeIds =
        IntStream.range(0, numEmployees)
            .mapToObj(i -> createEmployeeDTO())
            .map(
                e -> {
                  e.setSkills(activities);
                  e.setDaysAvailable(Sets.newHashSet(date.getDayOfWeek()));
                  return employeeController.saveEmployee(e).getId();
                })
            .collect(Collectors.toList());
    CustomerDTO cust = customerController.saveCustomer(createCustomerDTO());
    List<Long> petIds =
        IntStream.range(0, numPets)
            .mapToObj(i -> createPetDTO())
            .map(
                p -> {
                  p.setOwnerId(cust.getId());
                  try {
                    return petController.savePet(p).getId();
                  } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null;
                  }
                })
            .collect(Collectors.toList());
    return scheduleController.createSchedule(
        createScheduleDTO(petIds, employeeIds, date, activities));
  }
}
