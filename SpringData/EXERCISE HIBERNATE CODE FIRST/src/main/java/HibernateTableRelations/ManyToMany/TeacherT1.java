package HibernateTableRelations.ManyToMany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teachers")
public class TeacherT1 extends PersonT1 {
    private String speciality;

    public TeacherT1() {
    }
@Column(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
