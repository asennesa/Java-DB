package com.example.demo.services;

import com.example.demo.entities.UniversitySystem.Student;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


public interface StudentService {
    List<Student> findAllStudentsByCountOfCourses();

    List<Student> findAllByFirstName(String firstName);

    List<Student> findAllByAttendanceAndAverageGrade(String attendance,double averageGrade);

    List<Student> findAllByAverageGradeLessThenGreaterThen(double less,double greater);

    List<Student> findAllByFirstNameEndsWith(String str);

    List<Student> findAllByFirstNameContains(String str);

}
