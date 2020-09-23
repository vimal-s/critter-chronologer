package com.udacity.jdnd.course3.critter.presentation.user;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map to the database
 * directly.
 */
public class EmployeeDTO {

  private long id;

  @NotNull(message = "Employee name is required")
  private String name;

  @NotNull(message = "skills are required")
  private Set<EmployeeSkill> skills;

  private Set<DayOfWeek> daysAvailable;

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

  public Set<EmployeeSkill> getSkills() {
    return skills;
  }

  public void setSkills(Set<EmployeeSkill> skills) {
    this.skills = skills;
  }

  public Set<DayOfWeek> getDaysAvailable() {
    return daysAvailable;
  }

  public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
    this.daysAvailable = daysAvailable;
  }

  @Override
  public String toString() {
    return "EmployeeDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", skills="
        + (skills == null ? null : Arrays.toString(skills.toArray()))
        + ", daysAvailable="
        + (daysAvailable == null ? null : Arrays.toString(daysAvailable.toArray()))
        + '}';
  }
}
