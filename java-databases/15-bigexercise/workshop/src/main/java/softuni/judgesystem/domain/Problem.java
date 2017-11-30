package softuni.judgesystem.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@Table(name = "problems")
public class Problem {
    private Long id;
    private String name;
    private Set<User> contestants;
    private Set<Submission> submissions;
    private Set<ProblemTest> tests;
    private Contest contest;
    private Set<UserMaxResultPerProblem> usersMaxResults;

    public Problem() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", unique = false, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "problemsSolved", fetch = FetchType.EAGER)
    public Set<User> getContestants() {
        return contestants;
    }

    public void setContestants(Set<User> contestants) {
        this.contestants = contestants;
    }

    @OneToMany(mappedBy = "problem")
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    @OneToMany(mappedBy = "problem")
    public Set<ProblemTest> getTests() {
        return tests;
    }

    public void setTests(Set<ProblemTest> tests) {
        this.tests = tests;
    }

    @OneToMany(mappedBy = "problem")
    public Set<UserMaxResultPerProblem> getUsersMaxResults() {
        return usersMaxResults;
    }

    public void setUsersMaxResults(Set<UserMaxResultPerProblem> usersMaxResults) {
        this.usersMaxResults = usersMaxResults;
    }

    @ManyToOne(targetEntity = Contest.class)
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }
}
