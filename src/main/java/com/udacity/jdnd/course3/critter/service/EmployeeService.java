package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.data.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public Employee save(Employee employee) {
    return employeeRepository.save(employee);
  }

  public boolean exists(Long employeeId) {
    return employeeRepository.existsById(employeeId);
  }

  public Employee getEmployee(Long id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
  }

  public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
    Employee employee = getEmployee(id);
    employee.setDaysAvailable(daysAvailable);
    employeeRepository.save(employee);
  }

  public List<Employee> getEmployeesForService(
      Set<EmployeeSkill> requiredSkills, DayOfWeek requiredDay) {
    return employeeRepository.findAll().stream()
        .filter(
            employee ->
                employee.getDaysAvailable().stream().anyMatch(day -> day.equals(requiredDay))
                    && employee.getSkills().containsAll(requiredSkills))
        .collect(Collectors.toList());
  }
}