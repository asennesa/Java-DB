package entities.HospitalDatabase;

import entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private LocalDateTime date;
    private String comments;

    public Visitation() {
    }

    @Column(name = "date", nullable = false)
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column(name = "comments", nullable = false)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
