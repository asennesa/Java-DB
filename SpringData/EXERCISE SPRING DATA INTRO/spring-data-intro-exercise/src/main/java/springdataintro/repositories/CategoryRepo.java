package springdataintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdataintro.entities.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
}
