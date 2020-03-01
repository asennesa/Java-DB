package HibernateTableRelations.OneToManyANDManyToOne;

import HibernateTableRelations.OneToManyANDManyToOne.PersonT2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class TeacherT2 extends PersonT2 {
    private String speciality;
    private Set<CourseT2> courses;

    public TeacherT2() {
    }

    @Column(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @OneToMany(mappedBy = "name", targetEntity = CourseT2.class)
    public Set<CourseT2> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseT2> courses) {
        this.courses = courses;
    }
}
