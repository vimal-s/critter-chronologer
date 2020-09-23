package com.udacity.jdnd.course3.critter.presentation.user;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map to the database
 * directly.
 */
public class EmployeeRequestDTO {

  @NotNull(message = "date is required")
  private LocalDate date;

  private Set<EmployeeSkill> skills;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<EmployeeSkill> getSkills() {
    return skills == null ? Collections.emptySet() : Collections.unmodifiableSet(skills);
  }

  public void setSkills(Set<EmployeeSkill> skills) {
    this.skills = skills == null ? null : new HashSet<>(skills);
  }

  @Override
  public String toString() {
    return "EmployeeRequestDTO{"
        + "date="
        + date
        + ", skills="
        + (skills == null ? null : Arrays.toString(skills.toArray()))
        + '}';
  }
}
