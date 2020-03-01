package HibernateTableRelations.OneToManyANDManyToOne;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersonT2 extends HibernateTableRelations.OneToManyANDManyToOne.BaseEntityT2 {
    private String name;

    public PersonT2() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
