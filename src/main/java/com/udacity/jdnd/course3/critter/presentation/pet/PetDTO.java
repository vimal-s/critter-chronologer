package com.udacity.jdnd.course3.critter.presentation.pet;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map to the database
 * directly.
 */
public class PetDTO {

  // todo: using boxed type here causes tests to fail
  private long id;

  @NotNull(message = "type of pet is required")
  private PetType type;

  private String name;

  // todo: should I enforce owner
  // @NotNull(message = "Owner id is required")
  private Long ownerId;

  private LocalDate birthDate;

  private String notes;

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

  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "PetDTO{"
        + "id="
        + id
        + ", type="
        + type
        + ", name='"
        + name
        + '\''
        + ", ownerId="
        + ownerId
        + ", birthDate="
        + birthDate
        + ", notes='"
        + notes
        + '\''
        + '}';
  }
}
