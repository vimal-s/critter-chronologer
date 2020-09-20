package com.udacity.jdnd.course3.critter.data.employee;

import com.udacity.jdnd.course3.critter.presentation.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Nationalized;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    //    @JsonBackReference
    //    @ManyToOne
    //    @JoinColumn(name = "schedule_id")
    //    private Schedule schedule;

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

    //        public Schedule getSchedule() {
    //        Schedule copy = new Schedule();
    //        copy.setActivities(schedule.getActivities());
    //        copy.setDate(schedule.getDate());
    //        copy.setEmployeeIds(schedule.getEmployeeIds());
    //        copy.setId(schedule.getId());
    //        copy.setPetIds(schedule.getPetIds());
    //        return copy;
    //    }
    //
    //    public void setSchedule(Schedule schedule) {
    //        Schedule copy = new Schedule();
    //        copy.setActivities(schedule.getActivities());
    //        copy.setDate(schedule.getDate());
    //        copy.setEmployeeIds(schedule.getEmployeeIds());
    //        copy.setId(schedule.getId());
    //        copy.setPetIds(schedule.getPetIds());
    //        this.schedule = copy;
    //    }

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
                //                ", schedule=" + schedule +
                '}';
    }
}
