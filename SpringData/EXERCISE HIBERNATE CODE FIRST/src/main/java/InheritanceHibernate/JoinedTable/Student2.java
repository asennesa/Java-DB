package InheritanceHibernate.JoinedTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student2 extends JoinedTables {
    private double grade;

    public Student2() {
    }
@Column(name = "grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
