package softuni.judgesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softuni.judgesystem.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Category AS c SET c.category = :parent WHERE c.name IN :names")
    void updateParentForCategoryNames(@Param("names") List<String> nameList, @Param("parent") Category parent);
}
