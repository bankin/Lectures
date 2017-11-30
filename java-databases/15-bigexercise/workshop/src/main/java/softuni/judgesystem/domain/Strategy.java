package softuni.judgesystem.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "strategies")
public class Strategy {
    private Long id;
    private String name;
    private Set<Contest> contests;

    public Strategy() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "allowedStrategies")
    public Set<Contest> getContests() {
        return contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }
}
