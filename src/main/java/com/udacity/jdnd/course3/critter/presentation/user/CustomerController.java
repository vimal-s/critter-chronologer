package com.udacity.jdnd.course3.critter.presentation.user;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** Handles web requests related to Customers. */
@RestController
@RequestMapping("/user")
public class CustomerController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final CustomerService customerService;
  private final PetService petService;

  public CustomerController(CustomerService customerService, PetService petService) {
    this.customerService = customerService;
    this.petService = petService;
  }

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
    Customer customer = entityFrom(customerDTO);

    customer = customerService.save(customer);

    return dtoFrom(customer);
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    return customerService.getAllCustomers().stream()
        .map(this::dtoFrom)
        .collect(Collectors.toList());
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Throwable {
    Customer owner = petService.getOwnerByPet(petId);

    return dtoFrom(owner);
  }

  private Customer entityFrom(CustomerDTO customerDTO) {
    Customer customer = new Customer();

    BeanUtils.copyProperties(customerDTO, customer);

    return customer;
  }

  private CustomerDTO dtoFrom(Customer customer) {
    if (customer == null) {
      return null;
    }

    CustomerDTO customerDTO = new CustomerDTO();

    BeanUtils.copyProperties(customer, customerDTO);
    customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

    return customerDTO;
  }
}
