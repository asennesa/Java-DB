package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity  {

    private String firstName;
    private String lastName;
    private String position;
    private Card card;
    private Branch branch;

    public Employee() {
    }
    @Column(name ="first_name",nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name ="last_name",nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name ="position",nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    @OneToOne
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    @ManyToOne
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
