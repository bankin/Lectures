package softuni.judgesystem.services;

import org.springframework.stereotype.Service;
import softuni.judgesystem.domain.*;
import softuni.judgesystem.repositories.UserMaxResultPerContestRepository;
import softuni.judgesystem.repositories.UserMaxResultPerProblemRepository;
import softuni.judgesystem.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private UserMaxResultPerProblemRepository userMaxResultPerProblemRepository;
    private UserRepository userRepository;
    private UserMaxResultPerContestRepository userMaxResultPerContestRepository;

    public ResultService(UserMaxResultPerProblemRepository userMaxResultPerProblemRepository, UserRepository userRepository, UserMaxResultPerContestRepository userMaxResultPerContestRepository) {
        this.userMaxResultPerProblemRepository = userMaxResultPerProblemRepository;
        this.userRepository = userRepository;
        this.userMaxResultPerContestRepository = userMaxResultPerContestRepository;
    }

    public void updateProblemsMax(User user, Submission submission) {
        Optional<UserMaxResultPerProblem> any = user
                .getProblemsMaxResults()
                .stream()
                .filter(m -> m.getProblem().getId().equals(submission.getProblem().getId()))
                .findAny();

        if (any.isPresent()) {
            updateMax(submission, any.get());
        }
        else {
            createMax(user, submission);
        }
    }

    private void updateMax(Submission submission, UserMaxResultPerProblem maxResult) {
        if (submission.getPoints() > maxResult.getSubmission().getPoints()) {
            maxResult.setSubmission(submission);
        }
        else if (submission.getPoints().equals(maxResult.getSubmission().getPoints())) {
            if (submission.getPerformance() < maxResult.getSubmission().getPerformance()) {
                maxResult.setSubmission(submission);
            }
        }

        this.userMaxResultPerProblemRepository.save(maxResult);
    }

    private void createMax(User user, Submission submission) {
        UserMaxResultPerProblem maxResult = new UserMaxResultPerProblem();
        maxResult.setProblem(submission.getProblem());
        maxResult.setSubmission(submission);
        maxResult.setUser(user);

        user.addMaxResultForProblem(maxResult);

        this.userMaxResultPerProblemRepository.save(maxResult);
        this.userRepository.save(user);
    }

    public void updateContestMax(User user, Submission submission) {
        Optional<UserMaxResultPerContest> any = user.getContestsMaxResults()
                .stream()
                .filter(c -> c.getContest().getId().equals(submission.getProblem().getContest().getId()))
                .findAny();

        if (!any.isPresent()) {
            createMaxPerContest(user, submission);
        }
        else {
            updateMaxPerContest(user, submission, any.get());
        }
    }

    private void updateMaxPerContest(User user, Submission submission, UserMaxResultPerContest max) {
        List<UserMaxResultPerProblem> problemsInContest = user.getProblemsMaxResults()
                .stream()
                .filter(p -> p.getProblem().getContest().getId().equals(submission.getProblem().getContest().getId()))
                .collect(Collectors.toList());

        Optional<Double> totalPoints = problemsInContest
                .stream()
                .map(m -> m.getSubmission().getPoints())
                .reduce((aDouble, aDouble2) -> aDouble + aDouble2);

        Optional<Double> totalPerformance = problemsInContest
                .stream()
                .map(m -> m.getSubmission().getPerformance())
                .reduce((aDouble, aDouble2) -> aDouble + aDouble2);

        max.setPoints(totalPoints.get());
        max.setAveragePerformance(totalPerformance.get() / problemsInContest.size());

        this.userMaxResultPerContestRepository.save(max);
    }

    private void createMaxPerContest(User user, Submission submission) {
        UserMaxResultPerContest userMaxResultPerContest = new UserMaxResultPerContest();
        userMaxResultPerContest.setAveragePerformance(submission.getPerformance());
        userMaxResultPerContest.setContest(submission.getProblem().getContest());
        userMaxResultPerContest.setPoints(submission.getPoints());
        userMaxResultPerContest.setUser(user);

        user.addMaxResultForContest(userMaxResultPerContest);

        this.userRepository.save(user);
        this.userMaxResultPerContestRepository.save(userMaxResultPerContest);
    }
}
