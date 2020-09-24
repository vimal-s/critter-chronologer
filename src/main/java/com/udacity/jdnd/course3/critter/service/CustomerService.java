package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  public Customer getCustomer(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }
}
