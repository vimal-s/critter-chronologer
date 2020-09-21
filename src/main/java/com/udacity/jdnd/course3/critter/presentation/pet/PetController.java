package com.udacity.jdnd.course3.critter.presentation.pet;

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

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@Valid @RequestBody PetDTO petDTO) throws Throwable {
        logger.info("received from client: " + petDTO);

        Pet pet = dtoToEntity(petDTO);
        pet = petService.save(pet);
        logger.info("received from db: " + pet);

        return entityToDTO(pet);
    }

    private PetDTO entityToDTO(Pet pet) throws CloneNotSupportedException {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO, pet.getBirthDate() == null ? "birthDate" : "");
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        logger.info("changed to dto: " + petDTO);

        return petDTO;
    }

    private Pet dtoToEntity(PetDTO petDTO) throws Throwable {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet, petDTO.getBirthDate() == null ? "birthDate" : "");
        if (petDTO.getOwnerId() != 0) {
            Customer owner = userService.getCustomer(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        logger.info("changed to entity: " + pet);

        return pet;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }
}
