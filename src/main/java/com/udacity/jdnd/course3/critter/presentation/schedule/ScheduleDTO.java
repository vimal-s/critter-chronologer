package com.udacity.jdnd.course3.critter.presentation.schedule;

import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map to the database
 * directly.
 */
public class ScheduleDTO {

  private long id;

  @NotNull(message = "date is required")
  private LocalDate date;

  @NotEmpty(message = "employeeIds are required")
  private List<Long> employeeIds;

  @NotEmpty(message = "petIds are required")
  private List<Long> petIds;

  private Set<EmployeeSkill> activities;

  public List<Long> getEmployeeIds() {
    return employeeIds;
  }

  public void setEmployeeIds(List<Long> employeeIds) {
    this.employeeIds = employeeIds;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public List<Long> getPetIds() {
    return petIds;
  }

  public void setPetIds(List<Long> petIds) {
    this.petIds = petIds;
  }

  public Set<EmployeeSkill> getActivities() {
    return activities;
  }

  public void setActivities(Set<EmployeeSkill> activities) {
    this.activities = activities;
  }

  @Override
  public String toString() {
    return "ScheduleDTO{"
        + "id="
        + id
        + ", date="
        + date
        + ", employeeIds="
        + (employeeIds == null ? null : Arrays.toString(employeeIds.toArray()))
        + ", petIds="
        + (petIds == null ? null : Arrays.toString(petIds.toArray()))
        + ", activities="
        + (activities == null ? null : Arrays.toString(activities.toArray()))
        + '}';
  }
}
