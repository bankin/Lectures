package softuni.judgesystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "max_results_for_problems")
public class UserMaxResultPerProblem {
    private Long id;
    private User user;
    private Problem problem;
    private Submission submission;

    public UserMaxResultPerProblem() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Problem.class)
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @ManyToOne(targetEntity = Submission.class)
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
