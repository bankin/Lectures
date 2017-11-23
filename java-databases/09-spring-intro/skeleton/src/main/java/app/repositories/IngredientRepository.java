package app.repositories;

import app.model.ingredients.BasicIngredient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Basic;
import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<BasicIngredient, Long> {

    List<BasicIngredient> findByNameIn(List<String> names);

    @Transactional
    void deleteByName(String name);


    @Query("UPDATE BasicIngredient AS bi SET bi.price = bi.price + 0.2 * " +
            "bi.price WHERE bi.name IN :names")
    @Modifying
    @Transactional
    void updatePriceForNames(@Param("names") List<String> names);

    @Query("UPDATE BasicIngredient AS bi SET bi.price = bi.price + 0.2 * " +
            "bi.price")
    @Modifying
    @Transactional
    void updateAllPrices();
}
