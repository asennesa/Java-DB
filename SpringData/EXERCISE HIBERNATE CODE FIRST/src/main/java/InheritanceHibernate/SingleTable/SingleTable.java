package InheritanceHibernate.SingleTable;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SingleTable extends BaseEntity1 {
    private String name;

    public SingleTable() {
    }
    @Column(name = "name",nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
