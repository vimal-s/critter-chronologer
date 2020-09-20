package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.service.UserService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 *
 * <p>Includes requests for both customers and employees. Splitting this into separate user and
 * customer controllers would be fine too, though that is not part of the required scope for this
 * class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    logger.info("Received from client: " + customerDTO);
    Customer customer = dtoToEntity(customerDTO);
    customer = userService.save(customer);
    logger.info("Received from db: " + customer);
    customerDTO = entityToDto(customer);
    return customerDTO;
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable long employeeId) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(
      @RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
    throw new UnsupportedOperationException();
  }

  private Customer dtoToEntity(CustomerDTO customerDTO) {
    Customer customer = new Customer();
    BeanUtils.copyProperties(customerDTO, customer);
    logger.info("changed dto to entity: " + customer);
    return customer;
  }

  private CustomerDTO entityToDto(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer, customerDTO);
    logger.info("changed entity to dto: " + customerDTO);
    return customerDTO;
  }
}
