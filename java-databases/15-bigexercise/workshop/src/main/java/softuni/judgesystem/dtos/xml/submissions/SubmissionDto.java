package softuni.judgesystem.dtos.xml.submissions;

import softuni.judgesystem.dtos.xml.participations.IdDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "submission")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmissionDto {
    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "performance")
    private Double performance;

    @XmlElement(name = "user")
    private IdDto user;

    @XmlElement(name = "problem")
    private IdDto problem;

    @XmlElement(name = "points")
    private Double points;

    @XmlElement(name = "strategy")
    private IdDto strategy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPerformance() {
        return performance;
    }

    public void setPerformance(Double performance) {
        this.performance = performance;
    }

    public IdDto getUser() {
        return user;
    }

    public void setUser(IdDto user) {
        this.user = user;
    }

    public IdDto getProblem() {
        return problem;
    }

    public void setProblem(IdDto problem) {
        this.problem = problem;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public IdDto getStrategy() {
        return strategy;
    }

    public void setStrategy(IdDto strategy) {
        this.strategy = strategy;
    }
}
