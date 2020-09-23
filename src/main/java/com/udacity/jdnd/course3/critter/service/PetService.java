package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PetRepository petRepository;
  private final CustomerService customerService;

  public PetService(PetRepository petRepository, CustomerService customerService) {
    this.petRepository = petRepository;
    this.customerService = customerService;
  }

  public Pet save(Pet pet) throws CloneNotSupportedException {
    pet = petRepository.save(pet);

    Customer owner = pet.getOwner();
    owner.addPet(pet);
    customerService.save(owner);

    return pet;
  }

  public boolean exists(Long petId) {
    return petRepository.existsById(petId);
  }

  public Pet getPet(Long id) throws Throwable {
    return petRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Pet not found with id: " + id));
  }

  public List<Pet> getAllPets() {
    return petRepository.findAll();
  }

  public List<Pet> getPetsByOwner(Long id) {
    return petRepository.findByOwnerId(id);
  }

  public Customer getOwnerByPet(Long id) throws Throwable {
    return getPet(id).getOwner();
  }
}
