package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.data.pet.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class PetService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PetRepository petRepository;
  private final UserService userService;

  public PetService(PetRepository petRepository, UserService userService) {
    this.petRepository = petRepository;
    this.userService = userService;
  }

  public Pet save(Pet pet) throws CloneNotSupportedException {
    logger.info("Saving to database: " + pet);

    pet = petRepository.save(pet);

    // without this testing from spring fails
    Customer owner = pet.getOwner();
    List<Pet> pets = owner.getPets();
    if (pets == null) {
      pets = new ArrayList<>();
    } else {
      pets = new ArrayList<>(pets);
    }
    pets.add(pet);
    owner.setPets(pets);
    userService.save(owner);

    return pet;
  }

  public boolean exists(Long petId) {
    return petRepository.existsById(petId);
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
    // todo: I can throw exception here
    // assert getPet(id).getOwner() != null; todo: how to use assert statements
    return getPet(id).getOwner();
  }
}
