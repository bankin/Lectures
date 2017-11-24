package application.repositories;

import application.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM Product AS p " +
            "JOIN p.seller AS u " +
            "WHERE p.buyer IS NOT NULL" +
            "ORDER BY u.lastName, u.firstName")
    List<User> getAllSellers();
}
