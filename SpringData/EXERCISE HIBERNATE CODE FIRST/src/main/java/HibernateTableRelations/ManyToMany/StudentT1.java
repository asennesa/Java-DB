package HibernateTableRelations.ManyToMany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "students")
public class StudentT1 extends PersonT1 {
    private double grade;
    private Set<CourseT1> courses;

    public StudentT1() {
    }

    @Column(name = "grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
    //Bidirectional if you add this part.You can access both students and courses now.
    @ManyToMany(mappedBy = "students",/*mappedBy equals
                                       the name you gave to the set
                                       of the other class*/
                                      targetEntity = CourseT1.class)
    public Set<CourseT1> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseT1> courses) {
        this.courses = courses;
    }
}
