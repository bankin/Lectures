package softuni.judgesystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.judgesystem.domain.*;
import softuni.judgesystem.dtos.xml.submissions.SubmissionDto;
import softuni.judgesystem.repositories.ProblemRepository;
import softuni.judgesystem.repositories.StrategyRepository;
import softuni.judgesystem.repositories.SubmissionRepository;
import softuni.judgesystem.repositories.UserRepository;

import java.util.Optional;

@Service
public class SubmissionService {


    private UserRepository userRepository;
    private ProblemRepository problemRepository;
    private StrategyRepository strategyRepository;
    private SubmissionRepository submissionRepository;
    private UserService userService;
    private ResultService resultService;

    @Autowired
    public SubmissionService(UserRepository userRepository, ProblemRepository problemRepository, StrategyRepository strategyRepository, SubmissionRepository submissionRepository, UserService userService, ResultService resultService) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.strategyRepository = strategyRepository;
        this.submissionRepository = submissionRepository;
        this.userService = userService;
        this.resultService = resultService;
    }

    @Transactional
    public void submit(SubmissionDto submissionDto) {
        User user = ensureUserExists(submissionDto.getUser().getId());
        Problem problem = ensureProblemExists(submissionDto.getProblem().getId());

        ensureUserHasAccess(user, problem);

        Strategy strategy = ensureStrategyExists(submissionDto.getStrategy().getId());

        Submission submission = new Submission(submissionDto);
        submission.setUser(user);
        submission.setProblem(problem);
        submission.setUsedStrategy(strategy);

        this.submissionRepository.save(submission);

        this.userService.updateProblemsSolved(user, problem);

        this.resultService.updateProblemsMax(user, submission);

        this.resultService.updateContestMax(user, submission);
    }

    private Strategy ensureStrategyExists(Long id) {
        Strategy one = this.strategyRepository.findOne(id);

        if (one == null) {
            throw new IllegalArgumentException("Strategy does not exist");
        }

        return one;
    }

    private void ensureUserHasAccess(User user, Problem problem) {
        Contest contest = problem.getContest();

        Optional<Contest> first = user
                .getContestsParticipated()
                .stream()
                .filter(c -> c.getId().equals(contest.getId()))
                .findFirst();

        if (!first.isPresent()) {
            throw new RuntimeException("User has no access to this problem");
        }
    }

    private Problem ensureProblemExists(Long id) {
        Problem one = this.problemRepository.findOne(id);

        if (one == null) {
            throw new IllegalArgumentException("Problem does not exist");
        }

        return one;
    }

    private User ensureUserExists(Long id) {
        User one = this.userRepository.findOne(id);

        if (one == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        return one;
    }
}
