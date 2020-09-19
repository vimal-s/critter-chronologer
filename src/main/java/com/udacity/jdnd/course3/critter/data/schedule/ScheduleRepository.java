package com.udacity.jdnd.course3.critter.data.schedule;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findByEmployee(Long id);

  List<Schedule> findByPet(Long id);
}
