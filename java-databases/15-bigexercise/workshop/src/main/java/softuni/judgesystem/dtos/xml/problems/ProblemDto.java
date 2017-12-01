package softuni.judgesystem.dtos.xml.problems;

import softuni.judgesystem.dtos.xml.participations.IdDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "problem")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProblemDto {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "contest")
    private IdDto contest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdDto getContest() {
        return contest;
    }

    public void setContest(IdDto contest) {
        this.contest = contest;
    }
}
