package softuni.judgesystem.domain;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@Table(name = "contests")
public class Contest {
    private Long id;
    private String name;
    private Category category;
    private Set<Problem> problems;
    private Set<User> contestants;
    private Set<Strategy> allowedStrategies;
    private Set<UserMaxResultPerContest> maxResults;

    public Contest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Category.class)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @OneToMany(mappedBy = "contest")
    public Set<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }

    @ManyToMany(mappedBy = "contestsParticipated", fetch = FetchType.EAGER)
    public Set<User> getContestants() {
        return contestants;
    }

    public void setContestants(Set<User> contestants) {
        this.contestants = contestants;
    }

    public void addContestant(User user) {
        this.contestants.add(user);
    }

    @ManyToMany
    @JoinTable(name = "contests_strategies",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "strategy_id"))
    public Set<Strategy> getAllowedStrategies() {
        return allowedStrategies;
    }

    public void setAllowedStrategies(Set<Strategy> allowedStrategies) {
        this.allowedStrategies = allowedStrategies;
    }

    @OneToMany(mappedBy = "contest")
    public Set<UserMaxResultPerContest> getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Set<UserMaxResultPerContest> maxResults) {
        this.maxResults = maxResults;
    }
}
