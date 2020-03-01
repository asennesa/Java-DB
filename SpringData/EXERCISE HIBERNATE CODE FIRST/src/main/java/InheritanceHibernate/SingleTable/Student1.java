package InheritanceHibernate.SingleTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student1 extends SingleTable {
    private double grade;

    public Student1() {
    }
@Column(name = "grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
