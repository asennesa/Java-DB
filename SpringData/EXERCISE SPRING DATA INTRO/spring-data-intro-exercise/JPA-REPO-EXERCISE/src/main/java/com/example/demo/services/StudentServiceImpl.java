package com.example.demo.services;

import com.example.demo.entities.UniversitySystem.Student;
import com.example.demo.repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepo studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public List<Student> findAllStudentsByCountOfCourses() {
        return studentRepo.findStudentByCountOfCourses();

    }

    @Override
    public List<Student> findAllByFirstName(String firstName) {
        return this.studentRepo.findAllByFirstName(firstName.toUpperCase());
    }

    @Override
    public List<Student> findAllByAttendanceAndAverageGrade(String attendance, double averageGrade) {
        return this.studentRepo.findAllByAttendanceAndAverageGradeLessThan(attendance.toUpperCase(),averageGrade);
    }

    @Override
    public List<Student> findAllByAverageGradeLessThenGreaterThen(double less,double greater) {
        return this.studentRepo.findStudentsByAverageGradeLessThanOrAverageGradeGreaterThan(less,greater);
    }

    @Override
    public List<Student>findAllByFirstNameEndsWith(String str){
        return this.studentRepo.findAllByFirstNameEndingWith(str);
    }

    @Override
    public List<Student> findAllByFirstNameContains(String str){
        return this.studentRepo.findAllByFirstNameContains(str);
    }
}
