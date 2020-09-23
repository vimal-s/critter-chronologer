package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.presentation.pet.PetController;
import com.udacity.jdnd.course3.critter.presentation.pet.PetDTO;
import com.udacity.jdnd.course3.critter.presentation.pet.PetType;
import com.udacity.jdnd.course3.critter.presentation.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.presentation.user.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.udacity.jdnd.course3.critter.Helper.createCustomerDTO;
import static com.udacity.jdnd.course3.critter.Helper.createPetDTO;

@Transactional
@SpringBootTest(classes = CritterApplication.class)
public class PetTests {

  @Autowired private PetController petController;

  @Autowired private UserController userController;

  @Test
  public void testFindPetsByOwner() throws Throwable {
    CustomerDTO customerDTO = createCustomerDTO();
    CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

    PetDTO petDTO = createPetDTO();
    petDTO.setOwnerId(newCustomer.getId());
    PetDTO newPet = petController.savePet(petDTO);
    petDTO.setType(PetType.DOG);
    petDTO.setName("DogName");
    PetDTO newPet2 = petController.savePet(petDTO);

    List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId());
    Assertions.assertEquals(pets.size(), 2);
    Assertions.assertEquals(pets.get(0).getOwnerId(), newCustomer.getId());
    Assertions.assertEquals(pets.get(0).getId(), newPet.getId());
  }

  @Test
  public void testFindOwnerByPet() throws Throwable {
    CustomerDTO customerDTO = createCustomerDTO();
    CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

    PetDTO petDTO = createPetDTO();
    petDTO.setOwnerId(newCustomer.getId());
    PetDTO newPet = petController.savePet(petDTO);

    CustomerDTO owner = userController.getOwnerByPet(newPet.getId());
    Assertions.assertEquals(owner.getId(), newCustomer.getId());
    Assertions.assertEquals(owner.getPetIds().get(0), newPet.getId());
  }
}
