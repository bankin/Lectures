package softuni.judgesystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.judgesystem.domain.Category;
import softuni.judgesystem.domain.Contest;
import softuni.judgesystem.domain.Strategy;
import softuni.judgesystem.domain.User;
import softuni.judgesystem.dtos.json.CategoryDto;
import softuni.judgesystem.dtos.json.ContestDto;
import softuni.judgesystem.dtos.json.StrategyDto;
import softuni.judgesystem.dtos.json.UserDto;
import softuni.judgesystem.dtos.xml.participations.UserParticipation;
import softuni.judgesystem.dtos.xml.participations.UsersParticipationsDto;
import softuni.judgesystem.dtos.xml.problems.ProblemsDto;
import softuni.judgesystem.dtos.xml.submissions.SubmissionDto;
import softuni.judgesystem.dtos.xml.submissions.SubmissionsDto;
import softuni.judgesystem.repositories.CategoryRepository;
import softuni.judgesystem.repositories.ContestRepository;
import softuni.judgesystem.repositories.StrategyRepository;
import softuni.judgesystem.repositories.UserRepository;
import softuni.judgesystem.services.ContestService;
import softuni.judgesystem.services.ProblemService;
import softuni.judgesystem.services.SubmissionService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private ModelMapper modelMapper;
    private Gson gson;

    private CategoryRepository categoryRepository;
    private StrategyRepository strategyRepository;
    private ContestRepository contestRepository;
    private UserRepository userRepository;
    private ContestService contestService;
    private SubmissionService submissionService;
    private ProblemService problemService;

    @Autowired
    public ConsoleRunner(CategoryRepository categoryRepository, StrategyRepository strategyRepository, ContestRepository contestRepository, UserRepository userRepository, ContestService contestService, SubmissionService submissionService, ProblemService problemService) {
        this.categoryRepository = categoryRepository;
        this.strategyRepository = strategyRepository;
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
        this.contestService = contestService;
        this.submissionService = submissionService;
        this.problemService = problemService;

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public void run(String... strings) throws Exception {
        importCategories();
        importStrategies();
        importContests();
        importUsers();
        importParticipants();
        importProblems();
        importSubmissions();
    }

    private void importSubmissions() throws JAXBException {
        JAXBContext participantContext = JAXBContext.newInstance(SubmissionsDto.class);
        Unmarshaller unmarshaller = participantContext.createUnmarshaller();

        SubmissionsDto dto =
            (SubmissionsDto)
                unmarshaller.unmarshal(
                    new File("data/submissions.xml"));


        List<SubmissionDto> submissionsList = dto.getSubmissionDtoList();

        submissionsList
            .forEach(e -> this.submissionService.submit(e));
    }

    private void importProblems() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProblemsDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ProblemsDto dto =
            (ProblemsDto)
                unmarshaller.unmarshal(new File("data/problems.xml"));

        this.problemService.saveAll(dto.getProblemDtoList());
    }

    private void importParticipants() throws JAXBException {
        JAXBContext participantContext = JAXBContext.newInstance(UsersParticipationsDto.class);
        Unmarshaller unmarshaller = participantContext.createUnmarshaller();

        UsersParticipationsDto dto =
                (UsersParticipationsDto)
                    unmarshaller.unmarshal(new File("data/user_participations.xml"));

        List<UserParticipation> participationList = dto.getParticipationList();

        participationList
            .forEach(e ->
                this.contestService.enrollUserId(e.getContestId().getId(), e.getUserId().getId()));
    }

    private void importUsers() throws FileNotFoundException {
        UserDto[] userDtos = gson.fromJson(
                new FileReader("data/users.json"),
                UserDto[].class);

        List<User> users = Arrays.stream(userDtos)
                .map(dto -> this.modelMapper.map(dto, User.class))
                .collect(Collectors.toList());

        this.userRepository.save(users);
    }

    private void importContests() throws FileNotFoundException {
        ContestDto[] contestDtos = gson.fromJson(
                new FileReader("data/contests.json"),
                ContestDto[].class);

        for (ContestDto contestDto : contestDtos) {
            Contest contest = new Contest();
            contest.setName(contestDto.getName());

            Set<Strategy> strategies = getAllowedStrategies(contestDto);
            contest.setAllowedStrategies(strategies);

            Category category =
                    this.categoryRepository
                            .findByName(contestDto.getCategory().getName());
            contest.setCategory(category);

            this.contestRepository.save(contest);
        }
    }

    private Set<Strategy> getAllowedStrategies(ContestDto contestDto) {
        List<String> strategiesNames = contestDto
                .getAllowedStrategies()
                .stream()
                .map(StrategyDto::getName)
                .collect(Collectors.toList());

        return this.strategyRepository.findByNameIn(strategiesNames);
    }

    private void importStrategies() throws FileNotFoundException {
        StrategyDto[] strategyDtos = gson.fromJson(
                new FileReader("data/strategies.json"),
                StrategyDto[].class);

        List<Strategy> strategies = Arrays.stream(strategyDtos)
                .map(dto -> this.modelMapper.map(dto, Strategy.class))
                .collect(Collectors.toList());

        this.strategyRepository.save(strategies);
    }


    private void importCategories() throws FileNotFoundException, InterruptedException {
        CategoryDto[] categoryDtos = gson.fromJson(
                new FileReader("data/categories.json"),
                CategoryDto[].class);

        List<Category> categories = Arrays.stream(categoryDtos)
                .map(dto -> this.modelMapper.map(dto, Category.class))
                .collect(Collectors.toList());


        for (Category parent : categories) {
            saveAllWithoutRelations(parent.getCategories(), parent);
        }

        for (CategoryDto dto: categoryDtos) {
            updateRelations(dto.getCategories(), dto);
        }
    }

    private void updateRelations(Set<CategoryDto> categories, CategoryDto dto) {
        if (categories == null) return;

        Category parent = this.categoryRepository.findByName(dto.getName());

        List<String> nameList = categories
                .stream()
                .map(CategoryDto::getName)
                .collect(Collectors.toList());

        this.categoryRepository.updateParentForCategoryNames(nameList, parent);

        for (CategoryDto cat : categories) {
            updateRelations(cat.getCategories(), cat);
        }

    }

    private void saveAllWithoutRelations(Set<Category> categories, Category parent) throws InterruptedException {
        parent.setCategories(null);
        parent.setCategory(null);
        this.categoryRepository.save(parent);

        if (categories == null) { return; }

        for (Category cat : categories) {
            saveAllWithoutRelations(cat.getCategories(), cat);
        }
    }
}
