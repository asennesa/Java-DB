package InheritanceHibernate.TablePerClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends InheritanceHibernate.TablePerClass.Person {
    private double grade;

    public Student() {
    }
@Column(name = "grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
