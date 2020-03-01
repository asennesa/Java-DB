package InheritanceHibernate.TablePerClass;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends BaseEntity {
    private String name;

    public Person() {
    }
    @Column(name = "name",nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
