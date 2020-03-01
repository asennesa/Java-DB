package HibernateTableRelations.OneToManyANDManyToOne;

import HibernateTableRelations.OneToManyANDManyToOne.PersonT2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class StudentT2 extends PersonT2 {
    private double grade;

    public StudentT2() {
    }

    @Column(name = "grade")
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
