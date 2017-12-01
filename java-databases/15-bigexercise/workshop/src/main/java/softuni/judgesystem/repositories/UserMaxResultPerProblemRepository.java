package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softuni.judgesystem.domain.UserMaxResultPerProblem;

@Repository
public interface UserMaxResultPerProblemRepository extends CrudRepository<UserMaxResultPerProblem, Long>{

}
