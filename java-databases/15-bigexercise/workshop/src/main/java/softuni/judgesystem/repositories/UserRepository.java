package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import softuni.judgesystem.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
