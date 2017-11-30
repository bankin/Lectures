package softuni.judgesystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.judgesystem.domain.Contest;
import softuni.judgesystem.domain.User;
import softuni.judgesystem.repositories.ContestRepository;
import softuni.judgesystem.repositories.UserRepository;

import java.util.Optional;

@Service
public class ContestService {

    private UserRepository userRepository;
    private ContestRepository contestRepository;

    @Autowired
    public ContestService(UserRepository userRepository, ContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }

    public void enrollUserId(Long contestId, Long userId) {
        Contest contest = this.contestRepository.findOne(contestId);

        enrollUserId(contest, userId);
    }

    public void enrollUserId(Contest contest, Long userId) {
        User user = this.userRepository.findOne(userId);

        this.enroll(contest, user);
    }

    private void enroll(Contest contest, User user) {
        if (contest == null) {
            throw new IllegalArgumentException("Contest cannot be found");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be found");
        }

        Optional<Contest> first = user.getContestsParticipated()
                .stream()
                .filter(c -> c.equals(contest))
                .findFirst();

        if (first.isPresent()) {
            throw new RuntimeException("Used already has enrolled to this contest");
        }

        user.addContestParticipation(contest);
        contest.addContestant(user);

        this.userRepository.save(user);
        this.contestRepository.save(contest);
    }
}
