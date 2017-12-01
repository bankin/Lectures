package softuni.judgesystem.repositories;

import org.springframework.data.repository.CrudRepository;
import softuni.judgesystem.domain.Submission;

public interface SubmissionRepository extends CrudRepository<Submission, Long> {
}
