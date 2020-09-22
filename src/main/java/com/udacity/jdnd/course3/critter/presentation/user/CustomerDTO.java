package com.udacity.jdnd.course3.critter.presentation.user;

import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map to the database
 * directly.
 */
public class CustomerDTO {

  private long id;
  private String name;
  private String phoneNumber;
  private List<Long> petIds;
  private String notes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public List<Long> getPetIds() {
    return petIds;
  }

  public void setPetIds(List<Long> petIds) {
    this.petIds = petIds;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "CustomerDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", phoneNumber='"
        + phoneNumber
        + '\''
        + ", petIds="
        + petIds
        + ", notes='"
        + notes
        + '\''
        + '}';
  }
}
