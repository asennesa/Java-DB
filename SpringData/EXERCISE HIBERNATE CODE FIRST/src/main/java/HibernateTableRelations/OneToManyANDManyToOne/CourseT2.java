package HibernateTableRelations.OneToManyANDManyToOne;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class CourseT2 extends BaseEntityT2 {
    private String name;
    private TeacherT2 teacher;

    public CourseT2() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne /*Every course can have one teacher unidirectional if left like that.
Basically adding foreign key*/
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public TeacherT2 getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherT2 teacher) {
        this.teacher = teacher;
    }
}
