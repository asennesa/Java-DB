package InheritanceHibernate.JoinedTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher2 extends JoinedTables {
    private String speciality;

    public Teacher2() {
    }
@Column(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
