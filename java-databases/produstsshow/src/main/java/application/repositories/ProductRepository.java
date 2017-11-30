package application.repositories;

import application.entities.Product;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedStoredProcedureQuery;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal from, BigDecimal to);

//    @Query("CALL get_price_for(:name)")
//    BigDecimal getPriceForName(@Param("name") String name);

//    @Procedure(value = "get_price_for")
//    BigDecimal getPriceForName(@Param("n") String name);
}
