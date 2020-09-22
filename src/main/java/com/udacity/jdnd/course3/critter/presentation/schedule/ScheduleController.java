package com.udacity.jdnd.course3.critter.presentation.schedule;

import com.udacity.jdnd.course3.critter.data.schedule.Schedule;
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

  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @PostMapping
  public ScheduleDTO createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
    logger.info("received from client: " + scheduleDTO);

    Schedule schedule = dtoToEntity(scheduleDTO);
    schedule = scheduleService.create(schedule);
    logger.info("received from db: " + schedule);

    return entityToDTO(schedule);
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

  private ScheduleDTO entityToDTO(Schedule schedule) {
    ScheduleDTO scheduleDTO = new ScheduleDTO();
    BeanUtils.copyProperties(schedule, scheduleDTO);
    logger.info("changed to dto: " + scheduleDTO);
    return scheduleDTO;
  }

  private Schedule dtoToEntity(ScheduleDTO scheduleDTO) {
    Schedule schedule = new Schedule();
    BeanUtils.copyProperties(scheduleDTO, schedule);
    logger.info("changed to entity: " + schedule);
    return schedule;
  }

  private List<ScheduleDTO> dtoListFrom(List<Schedule> schedules) {
    return schedules.stream()
            .peek(schedule -> logger.info("received from db: " + schedule))
            .map(this::entityToDTO)
            .collect(Collectors.toList());
  }
}
