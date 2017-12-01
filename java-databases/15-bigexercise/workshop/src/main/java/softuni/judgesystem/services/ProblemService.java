package softuni.judgesystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.judgesystem.domain.Contest;
import softuni.judgesystem.domain.Problem;
import softuni.judgesystem.dtos.xml.problems.ProblemDto;
import softuni.judgesystem.repositories.ContestRepository;
import softuni.judgesystem.repositories.ProblemRepository;

import java.util.List;

@Service
public class ProblemService {


    private ContestRepository contestRepository;
    private ProblemRepository problemRepository;

    @Autowired
    public ProblemService(ContestRepository contestRepository, ProblemRepository problemRepository) {
        this.contestRepository = contestRepository;
        this.problemRepository = problemRepository;
    }

    public void saveAll(List<ProblemDto> problemDtoList) {
        problemDtoList.forEach(this::saveOne);
    }

    public void saveOne(ProblemDto e) {
        Contest contest = ensureContestExists(e.getContest().getId());

        Problem problem = new Problem();
        problem.setName(e.getName());
        problem.setContest(contest);

        this.problemRepository.save(problem);
    }

    private Contest ensureContestExists(Long id) {
        Contest one = this.contestRepository.findOne(id);

        if (one == null) {
            throw new IllegalArgumentException("Contest does not exist");
        }

        return one;
    }
}
