package com.udacity.jdnd.course3.critter.presentation.pet;

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

/** Handles web requests related to Pets. */
@RestController
@RequestMapping("/pet")
public class PetController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PetService petService;
  private final CustomerService customerService;

  public PetController(PetService petService, CustomerService customerService) {
    this.petService = petService;
    this.customerService = customerService;
  }

  @PostMapping
  public PetDTO savePet(@Valid @RequestBody PetDTO petDTO) throws Throwable {
    Pet pet = entityFrom(petDTO);
    pet = petService.save(pet);
    return dtoFrom(pet);
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) throws Throwable {
    Pet pet = petService.getPet(petId);
    return dtoFrom(pet);
  }

  @GetMapping
  public List<PetDTO> getPets() {
    List<Pet> pets = petService.getAllPets();
    return dtoListFrom(pets);
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<Pet> pets = petService.getPetsByOwner(ownerId);
    return dtoListFrom(pets);
  }

  private Pet entityFrom(PetDTO petDTO) throws Throwable {
    Pet pet = new Pet();

    BeanUtils.copyProperties(petDTO, pet, petDTO.getBirthDate() == null ? "birthDate" : "");
    Customer owner = customerService.getCustomer(petDTO.getOwnerId());
    pet.setOwner(owner);

    return pet;
  }

  private PetDTO dtoFrom(Pet pet) throws CloneNotSupportedException {
    PetDTO petDTO = new PetDTO();

    BeanUtils.copyProperties(pet, petDTO, pet.getBirthDate() == null ? "birthDate" : "");
    if (pet.getOwner() != null) {
      petDTO.setOwnerId(pet.getOwner().getId());
    }

    return petDTO;
  }

  private List<PetDTO> dtoListFrom(List<Pet> pets) {
    return pets.stream()
        .map(
            pet -> {
              try {
                return dtoFrom(pet);
              } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
              }
            })
        .collect(Collectors.toList());
  }
}
