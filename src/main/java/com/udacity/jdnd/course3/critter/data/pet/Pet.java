package com.udacity.jdnd.course3.critter.data.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.presentation.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  private PetType type;

  @Nationalized private String name;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Customer owner;

  private LocalDate birthDate;

  @Nationalized private String notes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PetType getType() {
    return type;
  }

  public void setType(PetType type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Customer getOwner() throws CloneNotSupportedException {
    return owner == null ? null : (Customer) owner.clone();
  }

  public void setOwner(Customer owner) throws CloneNotSupportedException {
    this.owner = (Customer) owner.clone();
  }

  public LocalDate getBirthDate() {
    return birthDate == null ? null : LocalDate.from(birthDate);
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = LocalDate.from(birthDate);
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  // accessing owner here causes StackOverflowError due to circular dependency between Pet and
  // Customer
  // I think this is related to Comparable interface in Customer class
  @Override
  public String toString() {
    return "Pet{"
        + "id="
        + id
        + ", type="
        + type
        + ", name='"
        + name
        + '\''
        + ", ownerId="
        + (owner == null ? null : owner.getId())
        + ", birthDate="
        + birthDate
        + ", notes='"
        + notes
        + '\''
        + '}';
  }
}
