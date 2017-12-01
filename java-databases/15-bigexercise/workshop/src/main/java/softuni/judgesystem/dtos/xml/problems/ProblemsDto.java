package softuni.judgesystem.dtos.xml.problems;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "problems")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProblemsDto {

    @XmlElement(name = "problem")
    private List<ProblemDto> problemDtoList;

    public List<ProblemDto> getProblemDtoList() {
        return problemDtoList;
    }

    public void setProblemDtoList(List<ProblemDto> problemDtoList) {
        this.problemDtoList = problemDtoList;
    }
}
