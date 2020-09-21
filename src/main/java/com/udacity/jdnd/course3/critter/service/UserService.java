package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.data.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

  public List<Employee> getEmployeesForService(
      Set<EmployeeSkill> requiredSkills, DayOfWeek requiredDay) {
    // todo: find employees with given skill and dayOfWeek here or in the repository
    logger.info("finding employees available on: " + requiredDay);

    return employeeRepository.findAll().stream()
        .filter(
            employee ->
                employee.getDaysAvailable().stream().anyMatch(day -> day.equals(requiredDay))
                    && employee.getSkills().containsAll(requiredSkills))
        .collect(Collectors.toList());
  }
}
