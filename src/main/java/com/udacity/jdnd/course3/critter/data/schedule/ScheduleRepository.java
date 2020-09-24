package com.udacity.jdnd.course3.critter.data.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findByEmployees_id(Long id);

  List<Schedule> findByPets_id(Long id);
}
