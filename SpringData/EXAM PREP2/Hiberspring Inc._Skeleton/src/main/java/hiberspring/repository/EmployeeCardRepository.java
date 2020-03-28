package hiberspring.repository;

import hiberspring.domain.entities.Card;
import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCardRepository extends JpaRepository<Card,Long> {
    Card findByNumber (String name);

}
