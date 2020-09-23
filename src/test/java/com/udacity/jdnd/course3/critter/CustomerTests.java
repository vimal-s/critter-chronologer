package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.presentation.pet.PetController;
import com.udacity.jdnd.course3.critter.presentation.pet.PetDTO;
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
public class CustomerTests {

  @Autowired private UserController userController;

  @Autowired private PetController petController;

  @Test
  public void testCreateCustomer() {
    CustomerDTO customerDTO = createCustomerDTO();
    CustomerDTO newCustomer = userController.saveCustomer(customerDTO);
    CustomerDTO retrievedCustomer = userController.getAllCustomers().get(0);
    Assertions.assertEquals(newCustomer.getName(), customerDTO.getName());
    Assertions.assertEquals(newCustomer.getId(), retrievedCustomer.getId());
    Assertions.assertTrue(retrievedCustomer.getId() > 0);
  }

  @Test
  public void testAddPetsToCustomer() throws Throwable {
    CustomerDTO customerDTO = createCustomerDTO();
    CustomerDTO newCustomer = userController.saveCustomer(customerDTO);

    PetDTO petDTO = createPetDTO();
    petDTO.setOwnerId(newCustomer.getId());
    PetDTO newPet = petController.savePet(petDTO);

    // make sure pet contains customer id
    PetDTO retrievedPet = petController.getPet(newPet.getId());
    Assertions.assertEquals(retrievedPet.getId(), newPet.getId());
    Assertions.assertEquals(retrievedPet.getOwnerId(), newCustomer.getId());

    // make sure you can retrieve pets by owner
    List<PetDTO> pets = petController.getPetsByOwner(newCustomer.getId());
    Assertions.assertEquals(newPet.getId(), pets.get(0).getId());
    Assertions.assertEquals(newPet.getName(), pets.get(0).getName());

    // check to make sure customer now also contains pet
    // todo: test fails here but runs successfully with postman
    CustomerDTO retrievedCustomer = userController.getAllCustomers().get(0);
    Assertions.assertTrue(
        retrievedCustomer.getPetIds() != null && retrievedCustomer.getPetIds().size() > 0);
    Assertions.assertEquals(retrievedCustomer.getPetIds().get(0), retrievedPet.getId());
  }
}
