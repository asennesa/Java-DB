package com.example.demo.AppController;

import com.example.demo.entities.UniversitySystem.Course;
import com.example.demo.entities.UniversitySystem.Student;
import com.example.demo.services.CourseService;
import com.example.demo.services.StudentService;
import com.example.demo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Controller
public class AppController implements CommandLineRunner {
    private CourseService courseService;
    private StudentService studentService;
    private TeacherService teacherService;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(CourseService courseService, StudentService studentService, TeacherService teacherService, BufferedReader bufferedReader) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {

        // getAllCoursesAfter2019();
        // getStudentsByCountOfCourses();
        // findAllLastNamesByFirstName();
        // findAllStudentsByAttendanceAndAverageGrade();
      //  findAllByAverageGreaterLessThenGreaterThen();
        //findAllCoursesThatStarterBeforeAndAfter();
        //findAllCoursesByStartDateBefore();
        //findByFirstNameEndsWith();
        findByFirstNameContains();

    }

    void getAllCoursesAfter2019() {
        List<Course> courses = this.courseService.getAllCoursesAfter2019();
        courses.forEach(c -> System.out.println(c.getName() + c.getStartDate()));
    }

    void getStudentsByCountOfCourses() {
        studentService.findAllStudentsByCountOfCourses().stream().forEach(s -> {
            System.out.println(String.format("%s %s - %d", s.getFirstName()
                    , s.getLastName(), s.getCourses().size()));
        });
    }

    void findAllLastNamesByFirstName() throws IOException {
        System.out.println("Enter student first name:");
        this.studentService.findAllByFirstName(this.bufferedReader.readLine()).stream()
                .map(Student::getLastName)
                .forEach(System.out::println);
    }

    void findAllStudentsByAttendanceAndAverageGrade() {
        this.studentService.findAllByAttendanceAndAverageGrade("0", 50.0).stream()
                .map(Student::getFirstName)
                .forEach(System.out::println);
    }

    void findAllByAverageGreaterLessThenGreaterThen() throws IOException {
        this.studentService.findAllByAverageGradeLessThenGreaterThen(6,60).stream()
                .map(Student::getFirstName)
                .forEach(System.out::println);

    }

    void findAllCoursesThatStarterBeforeAndAfter() throws IOException {
        System.out.println("Enter year :");
        int year = Integer.parseInt(bufferedReader.readLine());
        this.courseService.getAllCoursesStarterBeforeAndAfter(year).stream().map(Course::getName).forEach(System.out::println);
    }

    void findAllCoursesByStartDateBefore(){
        this.courseService.getAllByStartDateBefore("01-12-2020").stream().map(Course::getName).forEach(System.out::println);

    }

    void findByFirstNameEndsWith(){
        this.studentService.findAllByFirstNameEndsWith("n").forEach(e-> System.out.println(e.getFirstName()));
    }

    void findByFirstNameContains(){
        this.studentService.findAllByFirstNameContains("A").stream().map(Student::getFirstName).forEach(System.out::println);

    }





}
