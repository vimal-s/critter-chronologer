package com.udacity.jdnd.course3.critter.data.employee;

import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import org.hibernate.annotations.Nationalized;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

  @Id @GeneratedValue private Long id;

  @Nationalized private String name;

  @ElementCollection private Set<EmployeeSkill> skills;

  @ElementCollection private Set<DayOfWeek> daysAvailable;

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

  public Set<EmployeeSkill> getSkills() {
    return skills == null ? Collections.emptySet() : Collections.unmodifiableSet(skills);
  }

  public void setSkills(Set<EmployeeSkill> skills) {
    this.skills = skills == null ? Collections.emptySet() : new HashSet<>(skills);
  }

  public Set<DayOfWeek> getDaysAvailable() {
    return daysAvailable == null
        ? Collections.emptySet()
        : Collections.unmodifiableSet(daysAvailable);
  }

  public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
    this.daysAvailable =
        daysAvailable == null ? Collections.emptySet() : new HashSet<>(daysAvailable);
  }

  @Override
  public String toString() {
    return "Employee{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", skills="
        + skills
        + ", daysAvailable="
        + daysAvailable
        +
        '}';
  }
}
