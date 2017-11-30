package softuni.judgesystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "tests")
public class ProblemTest {
    private Long id;
    private String testContent;
    private String expectedResult;
    private Problem problem;

    public ProblemTest() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "test_content")
    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    @Column(name = "expected_result")
    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @ManyToOne(targetEntity = Problem.class)
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
