package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PetService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PetRepository petRepository;

  public PetService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  public Pet save(Pet pet) {
    logger.info("Saving to database: " + pet);
    return petRepository.save(pet);
  }

  public Pet getPet(Long id) throws Throwable {
    logger.info("Retrieving from db pet with id: " + id);
    return petRepository
        .findById(id)
        .orElseThrow(
            (Supplier<Throwable>) () -> new RuntimeException("Pet not found with id: " + id));
  }

  public List<Pet> getAllPets() {
    logger.info("Retrieving all pets from db");
    return petRepository.findAll();
  }

  public List<Pet> getPetsByOwner(Long id) {
    logger.info("Retrieving all pets with this owner from db: " + id);
    return petRepository.findByOwnerId(id);
  }

  public Customer getOwnerByPet(Long id) throws Throwable {
    logger.info("Retrieving owner with pet id: " + id);
    return getPet(id).getOwner();
  }

  public boolean exists(Long petId) {
    return petRepository.existsById(petId);
  }
}
