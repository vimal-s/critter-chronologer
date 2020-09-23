package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 */
@RestController
@RequestMapping("/user")
public class EmployeeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final UserService userService;

  public EmployeeController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
    logger.info("received from client: " + employeeDTO);

    Employee employee = entityFrom(employeeDTO);
    employee = userService.save(employee);
    logger.info("received from db: " + employee);

    employeeDTO = dtoFrom(employee);
    return employeeDTO;
  }

  // todo: change this to get mapping
  @GetMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Throwable {
    logger.info("received from client employeeId: " + employeeId);

    Employee employee = userService.getEmployee(employeeId);
    logger.info("received from db: " + employee);

    return dtoFrom(employee);
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(
      @RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Throwable {
    logger.info("received from client: " + daysAvailable + " " + employeeId);

    userService.setAvailability(daysAvailable, employeeId);
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(
      @Valid @RequestBody EmployeeRequestDTO employeeDTO) {
    logger.info("received from client: " + employeeDTO);
    return userService
        .getEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek())
        .stream()
        .peek(employee -> logger.info("received from db: " + employee))
        .map(this::dtoFrom)
        .collect(Collectors.toList());
  }


  private Employee entityFrom(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();

    BeanUtils.copyProperties(employeeDTO, employee);
    logger.info("changed dto to entity: " + employee);

    return employee;
  }

  private EmployeeDTO dtoFrom(Employee employee) {
    EmployeeDTO employeeDTO = new EmployeeDTO();

    BeanUtils.copyProperties(employee, employeeDTO);
    logger.info("changed entity to dto: " + employeeDTO);

    return employeeDTO;
  }
}
