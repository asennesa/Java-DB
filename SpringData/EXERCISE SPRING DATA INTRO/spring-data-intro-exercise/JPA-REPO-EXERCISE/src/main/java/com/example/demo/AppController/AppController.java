package com.example.demo.AppController;

import com.example.demo.entities.UniversitySystem.Course;
import com.example.demo.services.CourseService;
import com.example.demo.services.StudentService;
import com.example.demo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class AppController implements CommandLineRunner {
    private CourseService courseService;
    private StudentService studentService;
    private TeacherService teacherService;
    @Autowired
    public AppController(CourseService courseService, StudentService studentService, TeacherService teacherService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public void run(String... args) throws Exception {

        getAllCoursesAfter2019();

    }

    void getAllCoursesAfter2019(){
        List<Course> courses =this.courseService.getAllCoursesAfter2019();
        courses.forEach(c-> System.out.println(c.getName()+c.getStartDate()));
    }
}
