package softuni.judgesystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.judgesystem.domain.Problem;
import softuni.judgesystem.domain.User;
import softuni.judgesystem.repositories.UserRepository;

import java.util.Set;

@Service
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateProblemsSolved(User user, Problem problem) {
        Set<Problem> problemsSolved = user.getProblemsSolved();
        problemsSolved.add(problem);
        user.setProblemsSolved(problemsSolved);

        this.userRepository.save(user);
    }
}
