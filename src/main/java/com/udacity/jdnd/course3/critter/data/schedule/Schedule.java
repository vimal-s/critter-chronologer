package com.udacity.jdnd.course3.critter.data.schedule;

import com.udacity.jdnd.course3.critter.data.employee.Employee;
import com.udacity.jdnd.course3.critter.data.pet.Pet;
import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDate date;

  @ManyToMany
  @JoinTable(
      name = "employee_schedule",
      joinColumns = @JoinColumn(name = "schedule_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_id"))
  private List<Employee> employees;

  @ManyToMany
  @JoinTable(
      name = "pet_schedule",
      joinColumns = @JoinColumn(name = "schedule_id"),
      inverseJoinColumns = @JoinColumn(name = "pet_id"))
  private List<Pet> pets;

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

  public List<Employee> getEmployees() {
    return employees == null ? Collections.emptyList() : Collections.unmodifiableList(employees);
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees == null ? null : new ArrayList<>(employees);
  }

  public List<Pet> getPets() {
    return pets == null ? Collections.emptyList() : Collections.unmodifiableList(pets);
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets == null ? null : new ArrayList<>(pets);
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
        + ", employees="
        + Arrays.toString(getEmployees().toArray())
        + ", pets="
        + Arrays.toString(getPets().toArray())
        + ", activities="
        + Arrays.toString(getActivities().toArray())
        + '}';
  }
}
