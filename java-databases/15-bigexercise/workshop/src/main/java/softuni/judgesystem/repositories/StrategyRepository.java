package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import softuni.judgesystem.domain.Strategy;

import java.util.List;
import java.util.Set;

@Repository
public interface StrategyRepository extends CrudRepository<Strategy, Long> {
    Set<Strategy> findByNameIn(List<String> names);
}
