package softuni.judgesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softuni.judgesystem.domain.Category;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
