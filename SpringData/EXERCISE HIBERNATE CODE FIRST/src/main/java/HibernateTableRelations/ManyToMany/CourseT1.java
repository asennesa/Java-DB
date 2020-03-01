package HibernateTableRelations.ManyToMany;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courses")
public class CourseT1 extends BaseEntityT1 {
    private String name;
    private Set<StudentT1> students;

    public CourseT1() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Unidirectional if you leave it like that and not add anything
    //on the other side at students.
    @ManyToMany
    @JoinTable(name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    public Set<StudentT1> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentT1> students) {
        this.students = students;
    }
}
