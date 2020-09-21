package com.udacity.jdnd.course3.critter.data.schedule;

import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    //    @JsonManagedReference
    //    @OneToMany(mappedBy = "schedule")
    @ElementCollection
    private List<Long> employeeIds;

    //    @JsonManagedReference
    //    @OneToMany(mappedBy = "schedule")
    @ElementCollection
    private List<Long> petIds;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return LocalDate.from(date);
    }

    public void setDate(LocalDate date) {
        this.date = LocalDate.from(date);
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getPetIds() {
        return Collections.unmodifiableList(petIds);
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = new ArrayList<>(petIds);
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(
            Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", employeeIds=" + employeeIds +
                ", petIds=" + petIds +
                ", activities=" + activities +
                '}';
    }
}
