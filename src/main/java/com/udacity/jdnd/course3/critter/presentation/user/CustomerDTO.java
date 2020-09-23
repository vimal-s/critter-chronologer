package com.udacity.jdnd.course3.critter.presentation.user;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map to the database
 * directly.
 */
public class CustomerDTO {

  private long id;

  @NotNull(message = "Customer name is required")
  private String name;

  @NotNull(message = "Phone number is required")
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
        + (petIds == null ? null : Arrays.toString(petIds.toArray()))
        + ", notes='"
        + notes
        + '\''
        + '}';
  }
}
