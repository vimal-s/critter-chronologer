package com.udacity.jdnd.course3.critter.data.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.presentation.pet.PetType;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;

@Entity
public class Pet {

  @Id @GeneratedValue private Long id;

  @Nationalized private String name;

  private PetType type;

  private LocalDate birthDate;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Customer owner;

  //    @JsonBackReference
  //    @ManyToOne
  //    @JoinColumn(name = "schedule_id")
  //    private Schedule schedule;

  @Nationalized private String notes;

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

  public PetType getType() {
    return type;
  }

  public void setType(PetType type) {
    this.type = type;
  }

  public LocalDate getBirthDate() {
        return birthDate == null ? null : LocalDate.from(birthDate);
    }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = LocalDate.from(birthDate);
  }

  public Customer getOwner() throws CloneNotSupportedException {
    return owner == null ? null : (Customer) owner.clone();
  }

  public void setOwner(Customer owner) throws CloneNotSupportedException {
    this.owner = (Customer) owner.clone();
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  // accessing owner here causes StackOverflowError due to circular dependency between Pet and Customer
  // I think this is related to Comparable interface in Customer class
  @Override
  public String toString() {
    return "Pet{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", type=" + type +
      ", birthDate=" + birthDate +
      ", ownerId=" + (owner == null ? null : owner.getId()) +
      ", notes='" + notes + '\'' +
      '}';
    }
}
