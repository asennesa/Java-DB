package InheritanceHibernate.SingleTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher1 extends SingleTable {
    private String speciality;

    public Teacher1() {
    }
@Column(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
