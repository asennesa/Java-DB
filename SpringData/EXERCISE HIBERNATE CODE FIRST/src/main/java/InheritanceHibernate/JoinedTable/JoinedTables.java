package InheritanceHibernate.JoinedTable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class JoinedTables extends BaseEntity2 {
    private String name;

    public JoinedTables() {
    }
    @Column(name = "name",nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
