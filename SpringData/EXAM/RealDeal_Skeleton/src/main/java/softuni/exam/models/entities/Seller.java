package softuni.exam.models.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;

    public Seller() {
    }
    @Column(name="first_name")
    @Length(min=2,max=20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name="last_name")
    @Length(min=2,max=20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name="email",unique = true)
    @Pattern(regexp = "(.+@.+\\..+)")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    @Column(name ="town",nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
