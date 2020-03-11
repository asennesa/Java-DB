package com.example.demo.repositories;

import com.example.demo.entities.UniversitySystem.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {

    List<Course> findAllByStartDateAfter(LocalDateTime localDateTime);

    List<Course> findAllByStartDateBeforeOrStartDateAfter(LocalDateTime before,LocalDateTime after);

    List<Course> findAllByStartDateBefore(LocalDateTime localDateTime);
}
