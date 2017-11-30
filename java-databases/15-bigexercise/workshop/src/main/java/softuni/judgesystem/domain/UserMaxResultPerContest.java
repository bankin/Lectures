package softuni.judgesystem.domain;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "max_results_for_contests")
public class UserMaxResultPerContest {
    private Long id;
    private Double averagePerformance;
    private Double points;
    private User user;
    private Contest contest;

    public UserMaxResultPerContest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "average_performance")
    @Digits(integer=2, fraction=3)
    public Double getAveragePerformance() {
        return averagePerformance;
    }

    public void setAveragePerformance(Double averagePerformance) {
        this.averagePerformance = averagePerformance;
    }

    @Column(name = "overall_points")
    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Contest.class)
    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }
}
