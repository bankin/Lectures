package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import softuni.judgesystem.domain.Problem;

public interface ProblemRepository extends CrudRepository<Problem, Long> {
}
