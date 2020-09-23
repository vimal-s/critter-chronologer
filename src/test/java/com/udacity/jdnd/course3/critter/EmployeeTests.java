package com.udacity.jdnd.course3.critter;

import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.presentation.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.HelperClass.createEmployeeDTO;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class EmployeeTests {

  @Autowired private EmployeeController employeeController;

  @Test
  public void testCreateEmployee() throws Throwable {
    EmployeeDTO employeeDTO = createEmployeeDTO();
    EmployeeDTO newEmployee = employeeController.saveEmployee(employeeDTO);
    EmployeeDTO retrievedEmployee = employeeController.getEmployee(newEmployee.getId());
    Assertions.assertEquals(employeeDTO.getSkills(), newEmployee.getSkills());
    Assertions.assertEquals(newEmployee.getId(), retrievedEmployee.getId());
    Assertions.assertTrue(retrievedEmployee.getId() > 0);
  }

  @Test
  public void testChangeEmployeeAvailability() throws Throwable {
    EmployeeDTO employeeDTO = createEmployeeDTO();
    EmployeeDTO emp1 = employeeController.saveEmployee(employeeDTO);
    Assertions.assertTrue(emp1.getDaysAvailable().isEmpty());

    Set<DayOfWeek> availability =
        Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY);
    employeeController.setAvailability(availability, emp1.getId());

    EmployeeDTO emp2 = employeeController.getEmployee(emp1.getId());
    Assertions.assertEquals(availability, emp2.getDaysAvailable());
  }

  @Test
  public void testFindEmployeesByServiceAndTime() {
    EmployeeDTO emp1 = createEmployeeDTO();
    EmployeeDTO emp2 = createEmployeeDTO();
    EmployeeDTO emp3 = createEmployeeDTO();

    emp1.setDaysAvailable(
        Sets.newHashSet(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
    emp2.setDaysAvailable(
        Sets.newHashSet(DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    emp3.setDaysAvailable(Sets.newHashSet(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

    emp1.setSkills(Sets.newHashSet(EmployeeSkill.FEEDING, EmployeeSkill.PETTING));
    emp2.setSkills(Sets.newHashSet(EmployeeSkill.PETTING, EmployeeSkill.WALKING));
    emp3.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

    EmployeeDTO emp1n = employeeController.saveEmployee(emp1);
    EmployeeDTO emp2n = employeeController.saveEmployee(emp2);
    EmployeeDTO emp3n = employeeController.saveEmployee(emp3);

    // make a request that matches employee 1 or 2
    EmployeeRequestDTO er1 = new EmployeeRequestDTO();
    er1.setDate(LocalDate.of(2019, 12, 25)); // wednesday
    er1.setSkills(Sets.newHashSet(EmployeeSkill.PETTING));

    Set<Long> eIds1 =
        employeeController.findEmployeesForService(er1).stream()
            .map(EmployeeDTO::getId)
            .collect(Collectors.toSet());
    Set<Long> eIds1expected = Sets.newHashSet(emp1n.getId(), emp2n.getId());
    Assertions.assertEquals(eIds1, eIds1expected);

    // make a request that matches only employee 3
    EmployeeRequestDTO er2 = new EmployeeRequestDTO();
    er2.setDate(LocalDate.of(2019, 12, 27)); // friday
    er2.setSkills(Sets.newHashSet(EmployeeSkill.WALKING, EmployeeSkill.SHAVING));

    Set<Long> eIds2 =
        employeeController.findEmployeesForService(er2).stream()
            .map(EmployeeDTO::getId)
            .collect(Collectors.toSet());
    Set<Long> eIds2expected = Sets.newHashSet(emp3n.getId());
    Assertions.assertEquals(eIds2, eIds2expected);
  }
}
