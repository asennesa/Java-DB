package com.example.demo.services;

import com.example.demo.entities.UniversitySystem.Course;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {

    List<Course> getAllCoursesAfter2019();

    List<Course> getAllCoursesStarterBeforeAndAfter(int year );
    List<Course> getAllByStartDateBefore(String date);
}
