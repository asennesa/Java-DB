package com.example.demo.services;

import com.example.demo.entities.UniversitySystem.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCoursesAfter2019();
}
