package com.udacity.jdnd.course3.critter.data.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

  List<Pet> findByOwnerId(Long id);
}
