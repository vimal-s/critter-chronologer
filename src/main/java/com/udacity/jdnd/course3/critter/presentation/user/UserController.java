package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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
  private final PetService petService;

  public UserController(UserService userService, PetService petService) {
    this.userService = userService;
    this.petService = petService;
  }

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    logger.info("received from client: " + customerDTO);

    Customer customer = dtoToEntity(customerDTO);
    customer = userService.save(customer);
    logger.info("received from db: " + customer);

    customerDTO = entityToDto(customer);
    return customerDTO;
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    return userService.getAllCustomers().stream()
        .peek(customer -> logger.info("received from db: " + customer))
        .map(this::entityToDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Throwable {
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
    List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
    customerDTO.setPetIds(petIds);
    logger.info("changed entity to dto: " + customerDTO);

    return customerDTO;
  }
}
