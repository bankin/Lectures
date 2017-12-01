package softuni.judgesystem.domain;

import softuni.judgesystem.dtos.xml.submissions.SubmissionDto;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "submissions")
public class Submission {
    private Long id;
    private Double performance;
    private User user;
    private Problem problem;
    private Double points;
    private Strategy usedStrategy;

    public Submission() {
    }

    public Submission(SubmissionDto submissionDto) {
        this.performance = submissionDto.getPerformance();
        this.points = submissionDto.getPoints();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code_performance", nullable = false)
    @Digits(integer=2, fraction=3)
    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
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

    @Column(name = "points")
    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @ManyToOne(targetEntity = Strategy.class)
    public Strategy getUsedStrategy() {
        return usedStrategy;
    }

    public void setUsedStrategy(Strategy usedStrategy) {
        this.usedStrategy = usedStrategy;
    }
}
