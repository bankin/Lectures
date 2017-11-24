package application.repositories;

import application.entities.Product;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal from, BigDecimal to);
}
