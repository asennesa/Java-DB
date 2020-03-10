package com.example.demo.services;

import com.example.demo.entities.UniversitySystem.Course;
import com.example.demo.repositories.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepo courseRepo;

    @Autowired
    public CourseServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public List<Course> getAllCoursesAfter2019() {

        LocalDateTime startDate1 = LocalDateTime.of(2019,12,31,0,0);

        return this.courseRepo.findAllByStartDateAfter(startDate1);
    }
}
