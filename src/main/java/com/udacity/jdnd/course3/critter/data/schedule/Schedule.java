package com.udacity.jdnd.course3.critter.data.schedule;

import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Schedule {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  private LocalDate date;

  @ElementCollection private List<Long> employeeIds;

  @ElementCollection private List<Long> petIds;

  @ElementCollection private Set<EmployeeSkill> activities;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public List<Long> getEmployeeIds() {
    return employeeIds == null ? Collections.emptyList() : Collections.unmodifiableList(employeeIds);
  }

  public void setEmployeeIds(List<Long> employeeIds) {
    this.employeeIds = employeeIds == null ? null : new ArrayList<>(employeeIds);
  }

  public List<Long> getPetIds() {
    return petIds == null ? Collections.emptyList() : Collections.unmodifiableList(petIds);
  }

  public void setPetIds(List<Long> petIds) {
    this.petIds = petIds == null ? null : new ArrayList<>(petIds);
  }

  public Set<EmployeeSkill> getActivities() {
    return activities == null ? Collections.emptySet() : Collections.unmodifiableSet(activities);
  }

  public void setActivities(Set<EmployeeSkill> activities) {
    this.activities = activities == null ? null : new HashSet<>(activities);
  }

  @Override
  public String toString() {
    return "Schedule{"
        + "id="
        + id
        + ", date="
        + date
        + ", employeeIds="
        + Arrays.toString(getEmployeeIds().toArray())
        + ", petIds="
        + Arrays.toString(getPetIds().toArray())
        + ", activities="
        + Arrays.toString(getActivities().toArray())
        + '}';
  }
}
