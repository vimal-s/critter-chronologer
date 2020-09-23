package com.udacity.jdnd.course3.critter.data.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Customer implements Cloneable {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Nationalized private String name;

  private String phoneNumber;

  // todo: details about this annotation
  @JsonManagedReference
  @OneToMany(mappedBy = "owner")
  private List<Pet> pets;

  @Nationalized private String notes;

  public Customer() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Pet> getPets() {
    return pets == null ? Collections.emptyList() : Collections.unmodifiableList(pets);
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets == null ? null : new ArrayList<>(pets);
  }

  public boolean addPet(Pet pet) {
    if (pets == null) {
      pets = new ArrayList<>();
    }
    return pets.add(pet);
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", pets="
        + Arrays.toString(getPets().toArray())
        + ", notes='"
        + notes
        + '\''
        + '}';
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    super.clone();

    Customer customer = new Customer();
    customer.setId(getId());
    customer.setName(getName());
    customer.setPhoneNumber(getPhoneNumber());
    customer.setPets(getPets());
    customer.setNotes(getNotes());

    return customer;
  }
}
