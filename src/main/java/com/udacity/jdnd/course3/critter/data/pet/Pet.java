package com.udacity.jdnd.course3.critter.data.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.data.customer.Customer;
import com.udacity.jdnd.course3.critter.presentation.pet.PetType;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Nationalized;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    private PetType type;

    private LocalDate birthDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer owner;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "schedule_id")
//    private Schedule schedule;

    @Nationalized
    private String notes;

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

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return LocalDate.from(birthDate);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = LocalDate.from(birthDate);
    }

    public Customer getOwner() throws CloneNotSupportedException {
        return (Customer) owner.clone();
    }

    public void setOwner(Customer owner) throws CloneNotSupportedException {
        this.owner = (Customer) owner.clone();
    }

//    public Schedule getSchedule() {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", birthDate=" + birthDate +
                ", owner=" + owner +
//                ", schedule=" + schedule +
                ", notes='" + notes + '\'' +
                '}';
    }
}
