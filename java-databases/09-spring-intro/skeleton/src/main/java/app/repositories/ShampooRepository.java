package app.repositories;

import app.model.enums.Size;
import app.model.ingredients.Ingredient;
import app.model.shampoos.BasicShampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<BasicShampoo, Long> {

    List<BasicShampoo> findBySizeOrderByBrand(Size size);

    Long countByPriceLessThan(BigDecimal price);

    @Query("SELECT i.shampoos " +
            "FROM BasicIngredient AS i " +
            "WHERE i IN :ingredients")
    List<BasicShampoo> findByHavingIngredients(@Param("ingredients") List<? extends Ingredient> ingredients);


    @Query("SELECT i.shampoos " +
            "FROM BasicIngredient AS i " +
            "WHERE i.id IN :ingredientIds")
    List<BasicShampoo> findByHavingIngredientIds(@Param("ingredientIds") List<Long> ingredientIds);


    @Query("SELECT s FROM BasicShampoo AS s WHERE s.brand = :name")
    List<BasicShampoo> findByHavingName(@Param("name") String name);
}
