package com.udacity.jdnd.course3.critter.data.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Customer implements Cloneable {

  @Id @GeneratedValue private Long id;

  @Nationalized private String name;

  private String phoneNumber;

  @JsonManagedReference
  @OneToMany(mappedBy = "owner")
  private List<Pet> pets;

  @Nationalized private String notes;

  public Customer() {}

  // remove this constructor later
  public Customer(Long id, String name, String phoneNumber, List<Pet> pets, String notes) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.pets = new ArrayList<>(pets);
    this.notes = notes;
  }

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

  // todo: null check here
  public List<Pet> getPets() {
    return pets == null ? Collections.emptyList() : Collections.unmodifiableList(pets);
  }

  public void setPets(List<Pet> pets) {
    this.pets = new ArrayList<>(pets);
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
        + pets
        + ", notes='"
        + notes
        + '\''
        + '}';
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    super.clone();
    return new Customer(getId(), getName(), getPhoneNumber(), getPets(), getNotes());
  }
}
