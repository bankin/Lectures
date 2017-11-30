package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softuni.judgesystem.domain.Contest;

@Repository
public interface ContestRepository extends CrudRepository<Contest, Long> {
}
