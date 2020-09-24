package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Handles web requests related to Employees. */
@RestController
@RequestMapping("/user")
public class EmployeeController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
    Employee employee = entityFrom(employeeDTO);

    employee = employeeService.save(employee);

    return dtoFrom(employee);
  }

  @GetMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable long employeeId) {
    Employee employee = employeeService.getEmployee(employeeId);

    return dtoFrom(employee);
  }

  @PutMapping("/employee/{employeeId}")
  public EmployeeDTO setAvailability(
      @RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
    Employee employee = employeeService.setAvailability(daysAvailable, employeeId);

    return dtoFrom(employee);
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(
      @Valid @RequestBody EmployeeRequestDTO employeeDTO) {
    return employeeService
        .getEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek())
        .stream()
        .map(this::dtoFrom)
        .collect(Collectors.toList());
  }

  private Employee entityFrom(EmployeeDTO employeeDTO) {
    Employee employee = new Employee();

    BeanUtils.copyProperties(employeeDTO, employee);

    return employee;
  }

  private EmployeeDTO dtoFrom(Employee employee) {
    EmployeeDTO employeeDTO = new EmployeeDTO();

    BeanUtils.copyProperties(employee, employeeDTO);

    return employeeDTO;
  }
}
