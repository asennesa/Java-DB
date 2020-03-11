package com.example.demo.repositories;

import com.example.demo.entities.UniversitySystem.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student AS s ORDER BY s.courses.size DESC")
    List<Student>findStudentByCountOfCourses();

    List<Student> findAllByFirstName(String firstName);

    List<Student> findAllByAttendanceAndAverageGradeLessThan(String attendance,double averageGrade);

    List<Student> findStudentsByAverageGradeLessThanOrAverageGradeGreaterThan(double less,double greater);

    List<Student> findAllByFirstNameEndingWith(String str);

    List<Student>findAllByFirstNameContains(String str);

}
