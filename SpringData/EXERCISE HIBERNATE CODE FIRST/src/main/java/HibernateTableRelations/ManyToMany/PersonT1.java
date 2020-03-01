package HibernateTableRelations.ManyToMany;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersonT1 extends BaseEntityT1 {
    private String name;

    public PersonT1() {
    }
    @Column(name = "name",nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
