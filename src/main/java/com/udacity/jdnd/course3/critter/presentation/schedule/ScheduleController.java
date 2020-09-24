package com.udacity.jdnd.course3.critter.presentation.schedule;

import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** Handles web requests related to Schedules. */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final ScheduleService scheduleService;
  private final EmployeeService employeeService;
  private final PetService petService;

  public ScheduleController(
      ScheduleService scheduleService, EmployeeService employeeService, PetService petService) {
    this.scheduleService = scheduleService;
    this.employeeService = employeeService;
    this.petService = petService;
  }

  @PostMapping
  public ScheduleDTO createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
    Schedule schedule = entityFrom(scheduleDTO);

    schedule = scheduleService.create(schedule);

    return dtoFrom(schedule);
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    List<Schedule> schedules = scheduleService.getAllSchedules();

    return dtoListFrom(schedules);
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    List<Schedule> schedules = scheduleService.getAllByPet(petId);

    return dtoListFrom(schedules);
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    List<Schedule> schedules = scheduleService.getAllByEmployee(employeeId);

    return dtoListFrom(schedules);
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    List<Schedule> schedules = scheduleService.getAllByOwner(customerId);

    return dtoListFrom(schedules);
  }

  private ScheduleDTO dtoFrom(Schedule schedule) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();

    BeanUtils.copyProperties(schedule, scheduleDTO);
    scheduleDTO.setEmployeeIds(getEmployeeIds(schedule));
    scheduleDTO.setPetIds(getPetIds(schedule));

    return scheduleDTO;
  }

  private Schedule entityFrom(ScheduleDTO scheduleDTO) {
    Schedule schedule = new Schedule();

    BeanUtils.copyProperties(scheduleDTO, schedule);
    schedule.setEmployees(getEmployees(scheduleDTO));
    schedule.setPets(getPets(scheduleDTO));

    return schedule;
  }

  private List<ScheduleDTO> dtoListFrom(List<Schedule> schedules) {
    return schedules.stream().map(this::dtoFrom).collect(Collectors.toList());
  }

  private List<Employee> getEmployees(ScheduleDTO scheduleDTO) {
    return scheduleDTO.getEmployeeIds().stream()
        .map(employeeService::getEmployee)
        .collect(Collectors.toList());
  }

  private List<Pet> getPets(ScheduleDTO scheduleDTO) {
    return scheduleDTO.getPetIds().stream().map(petService::getPet).collect(Collectors.toList());
  }

  private List<Long> getEmployeeIds(Schedule schedule) {
    return schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
  }

  private List<Long> getPetIds(Schedule schedule) {
    return schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
  }
}
