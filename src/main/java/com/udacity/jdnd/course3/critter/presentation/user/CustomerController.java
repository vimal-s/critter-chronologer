package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Customers.
 */
@RestController
@RequestMapping("/user")
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final UserService userService;
  private final PetService petService;

  public CustomerController(UserService userService, PetService petService) {
    this.userService = userService;
    this.petService = petService;
  }

  // todo: fix issue when petIds are received
  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
    logger.info("received from client: " + customerDTO);

    Customer customer = entityFrom(customerDTO);
    customer = userService.save(customer);
    logger.info("received from db: " + customer);

    customerDTO = dtoFrom(customer);
    return customerDTO;
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    return userService.getAllCustomers().stream()
        .peek(customer -> logger.info("received from db: " + customer))
        .map(this::dtoFrom)
        .collect(Collectors.toList());
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Throwable {
    Customer owner = petService.getOwnerByPet(petId);
    logger.info("received from db: " + owner);

    return dtoFrom(owner);
  }
  // todo: combine customer dto and employee dto conversion methods
  // todo: copy petIds also
  private Customer entityFrom(CustomerDTO customerDTO) {
    Customer customer = new Customer();

    BeanUtils.copyProperties(customerDTO, customer);
    logger.info("changed dto to entity: " + customer);

    return customer;
  }

  private CustomerDTO dtoFrom(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    if (customer == null) {
      return null;
    }
    BeanUtils.copyProperties(customer, customerDTO);
    List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
    customerDTO.setPetIds(petIds);
    logger.info("changed entity to dto: " + customerDTO);

    return customerDTO;
  }
}
