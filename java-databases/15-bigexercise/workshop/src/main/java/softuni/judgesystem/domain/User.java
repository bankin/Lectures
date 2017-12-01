package softuni.judgesystem.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String username;
    private Set<UserMaxResultPerContest> contestsMaxResults;
    private Set<UserMaxResultPerProblem> problemsMaxResults;
    private Set<Problem> problemsSolved;
    private Set<Contest> contestsParticipated;
    private Set<Submission> submissions;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "user")
    public Set<UserMaxResultPerContest> getContestsMaxResults() {
        return contestsMaxResults;
    }

    public void setContestsMaxResults(Set<UserMaxResultPerContest> contestsMaxResults) {
        this.contestsMaxResults = contestsMaxResults;
    }

    @OneToMany(mappedBy = "user")
    public Set<UserMaxResultPerProblem> getProblemsMaxResults() {
        return problemsMaxResults;
    }

    public void setProblemsMaxResults(Set<UserMaxResultPerProblem> problemsMaxResults) {
        this.problemsMaxResults = problemsMaxResults;
    }

    @ManyToMany
    @JoinTable(name = "users_problems",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id"))
    public Set<Problem> getProblemsSolved() {
        return problemsSolved;
    }

    public void setProblemsSolved(Set<Problem> problemsSolved) {
        this.problemsSolved = problemsSolved;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_participations",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "contest_id"))
    public Set<Contest> getContestsParticipated() {
        return contestsParticipated;
    }

    public void setContestsParticipated(Set<Contest> contestsParticipated) {
        this.contestsParticipated = contestsParticipated;
    }

    @OneToMany(mappedBy = "user")
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    public void addContestParticipation(Contest contest) {
        this.contestsParticipated.add(contest);
    }

    public void addMaxResultForProblem(UserMaxResultPerProblem maxResult) {
        this.problemsMaxResults.add(maxResult);
    }

    public void addMaxResultForContest(UserMaxResultPerContest userMaxResultPerContest) {
        this.contestsMaxResults.add(userMaxResultPerContest);
    }
}
