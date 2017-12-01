package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softuni.judgesystem.domain.UserMaxResultPerContest;

@Repository
public interface UserMaxResultPerContestRepository
        extends CrudRepository<UserMaxResultPerContest, Long>{
}
