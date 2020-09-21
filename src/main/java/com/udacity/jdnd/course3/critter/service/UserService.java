package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.data.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final CustomerRepository customerRepository;
  private final EmployeeRepository employeeRepository;

  public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
    this.customerRepository = customerRepository;
    this.employeeRepository = employeeRepository;
  }

  public Customer save(Customer customer) {
    logger.info("Saving to database: " + customer);
    return customerRepository.save(customer);
  }

  public Customer getCustomer(Long id) throws Throwable {
    logger.info("finding customer with id: " + id);
    return customerRepository
            .findById(id)
            .orElseThrow(
                    (Supplier<Throwable>) () -> new RuntimeException("Customer not found with id: " + id));
    }

  public List<Customer> getAllCustomers() {
    logger.info("Retrieving all customers from database");
    return customerRepository.findAll();
  }

  public Employee save(Employee employee) {
    logger.info("Saving to database: " + employee);
    return employeeRepository.save(employee);
  }

  public Employee getEmployee(Long id) throws Throwable {
    logger.info("Retrieving employee from database with id: " + id);
    return employeeRepository
        .findById(id)
        .orElseThrow(
            (Supplier<Throwable>) () -> new RuntimeException("Employee not found with id: " + id));
  }

  public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) throws Throwable {
    Employee employee = getEmployee(id);

    logger.info("Setting availability days to database of employee with id: " + id);
    employee.setDaysAvailable(daysAvailable);

    employeeRepository.save(employee);
  }

  public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();
    // find employees with given skill and dayOfWeek here or in the repository
    // todo: replace this later
    logger.info("finding employees: " + date.toString());
    return employeeRepository.findAll().stream()
        .filter(
            employee ->
                employee.getDaysAvailable().stream().anyMatch(dayOfWeek -> dayOfWeek.equals(day))
                    && employee.getSkills().stream()
                        .anyMatch(employeeSkill -> skills.stream().anyMatch(employeeSkill::equals)))
        .collect(Collectors.toList());
  }

  public boolean exists(Long employeeId) {
    return employeeRepository.existsById(employeeId);
  }
}
