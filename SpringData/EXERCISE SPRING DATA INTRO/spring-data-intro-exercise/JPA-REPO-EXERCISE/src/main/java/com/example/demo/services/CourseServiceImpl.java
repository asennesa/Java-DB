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

    @Override
    public List<Course> getAllCoursesStarterBeforeAndAfter(int year) {
        LocalDateTime before = LocalDateTime.of(year,1,1,0,0);
        LocalDateTime after = LocalDateTime.of(year,12,31,0,0);

        return this.courseRepo.findAllByStartDateBeforeOrStartDateAfter(before,after);
    }

    @Override
    public List<Course> getAllByStartDateBefore(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        // FORMATTER WONT WORK ! Change to LocalDate instead of LocalDateTime.
        return this.courseRepo.findAllByStartDateBefore(localDateTime);
    }
}
